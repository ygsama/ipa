package io.github.ygsama.oauth2server.service.impl;


import io.github.ygsama.oauth2server.domain.SysRoleDO;
import io.github.ygsama.oauth2server.domain.SysUserDO;
import io.github.ygsama.oauth2server.dto.BaseDTO;
import io.github.ygsama.oauth2server.dto.PageDTO;
import io.github.ygsama.oauth2server.dto.RoleDTO;
import io.github.ygsama.oauth2server.dto.UserDTO;
import io.github.ygsama.oauth2server.mapstruct.SysRoleConverter;
import io.github.ygsama.oauth2server.mapstruct.SysUserConverter;
import io.github.ygsama.oauth2server.repository.SysUserMapper;
import io.github.ygsama.oauth2server.service.UserService;
import io.github.ygsama.oauth2server.utils.ResultEnum;
import io.github.ygsama.oauth2server.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 杨光
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(SysUserMapper sysUserMapper, PasswordEncoder passwordEncoder) {
        this.sysUserMapper = sysUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 用户详情查询：用户详情，机构，用户角色，用户菜单
     *
     * @param username 用户名
     * @return UserDTO
     */
    @Override
    public UserDTO getUserDetailByUsername(String username) {
        UserDTO dto;
        try {
            SysUserDO user = sysUserMapper.selectByPrimaryKeyWithMenu(username);
            dto = SysUserConverter.INSTANCE.domain2dto(user);
            dto.setResult(ResultEnum.SUCCEED);
        } catch (Exception e) {
            log.error("[查询用户信息异常]: ", e);
            return new UserDTO(ResultEnum.FAIL);
        }
        return dto;
    }

    @Override
    public PageDTO<UserDTO> getUserList(Map<String, Object> map) {
        PageDTO<UserDTO> dto;
        try {
            dto = new PageDTO<>(ResultEnum.SUCCEED);
            int curPage = StringUtil.objectToInt(map.get("curPage"));
            int pageSize = StringUtil.objectToInt(map.get("pageSize"));

            map.put("startRow", pageSize * (curPage - 1));
            map.put("endRow", pageSize * curPage);

            int totalRow = sysUserMapper.queryTotalRow(map);
            List<SysUserDO> userListDO = sysUserMapper.queryByPage(map);
            List<UserDTO> dtoList = SysUserConverter.INSTANCE.domain2dto(userListDO);

            dto.setCurPage(curPage);
            dto.setPageSize(pageSize);
            dto.setTotalPage(totalRow / pageSize + 1);
            dto.setTotalRow(totalRow);
            dto.setRetList(dtoList);
        } catch (Exception e) {
            log.error("[查询分页用户列表]: ", e);
            dto = new PageDTO<>(ResultEnum.FAIL);
        }
        return dto;
    }

    @Override
    public BaseDTO addUser(UserDTO dto) {
        try {
            SysUserDO domain = new SysUserDO();
            domain.setUsername(dto.getUsername());
            domain.setPassword(passwordEncoder.encode(dto.getPassword()).replace("{bcrypt}", ""));
            domain.setName(dto.getName());
            domain.setPhone(dto.getPhone());
            domain.setMobile(dto.getMobile());
            domain.setEmail(dto.getEmail());
            domain.setPhoto(dto.getPhoto());

            if (sysUserMapper.insert(domain) == 1) {
                List<RoleDTO> roleList = dto.getRoleList();
                List<SysRoleDO> doList = SysRoleConverter.INSTANCE.dto2do(roleList);
                if (roleList.size() != 0) {
                    sysUserMapper.insertUserRole(dto.getUsername(), doList);
                }
                return new BaseDTO(ResultEnum.SUCCEED);
            }
        } catch (Exception e) {
            log.error("[添加用户信息异常]: ", e);
        }
        return new BaseDTO(ResultEnum.FAIL);
    }

    @Override
    public BaseDTO updateUser(UserDTO dto) {
        try {
            SysUserDO domain = SysUserConverter.INSTANCE.dto2do(dto);
            List<RoleDTO> roleList = dto.getRoleList();
            sysUserMapper.updateByPrimaryKey(domain);

            if (roleList.size() != 0) {
                List<SysRoleDO> doList = SysRoleConverter.INSTANCE.dto2do(roleList);
                sysUserMapper.deleteUserRole(dto.getUsername());
                sysUserMapper.insertUserRole(dto.getUsername(), doList);
            }
            return new BaseDTO(ResultEnum.SUCCEED);
        } catch (Exception e) {
            log.error("[修改用户信息异常]: ", e);
        }
        return new BaseDTO(ResultEnum.FAIL);
    }

    @Override
    public BaseDTO deleteByUsername(String username) {
        try {
            sysUserMapper.deleteUserRole(username);
            if (sysUserMapper.deleteByPrimaryKey(username) == 1) {
                return new BaseDTO(ResultEnum.SUCCEED);
            }
            return new BaseDTO(ResultEnum.FAIL);
        } catch (Exception e) {
            log.error("[删除用户异常]: ", e);
            return new BaseDTO(ResultEnum.FAIL);
        }
    }

    /**
     * 用户修改密码
     *
     * @param userDTO password newPassword
     * @return BaseDTO
     */
    @Override
    public BaseDTO modPassword(UserDTO userDTO) {
        try {
            SysUserDO userDO = (SysUserDO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDO.getUsername();
            String password = userDTO.getPassword();
            String newPassword = passwordEncoder.encode(userDTO.getNewPassword()).replace("{bcrypt}", "");
            if (passwordEncoder.matches(password, userDO.getPassword())) {
                if (sysUserMapper.modPassword(username, newPassword) == 1) {
                    return new BaseDTO(ResultEnum.SUCCEED);
                }
            }
            return new BaseDTO(ResultEnum.FAIL);
        } catch (Exception e) {
            log.error("[用户修改密码异常]: ", e);
            return new BaseDTO(ResultEnum.FAIL);
        }
    }

    /**
     * 管理员重置密码
     *
     * @param userDTO username newPassword
     * @return BaseDTO
     */
    @Override
    public BaseDTO resetPassword(UserDTO userDTO) {
        try {
            SysUserDO userDO = (SysUserDO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDTO.getUsername();

            if (sysUserMapper.hasPermission(username) == 1) {
                String newPassword = passwordEncoder.encode(userDTO.getNewPassword()).replace("{bcrypt}", "");
                if (sysUserMapper.modPassword(username, newPassword) == 1) {
                    return new BaseDTO(ResultEnum.SUCCEED);
                }
            }
            return new BaseDTO(ResultEnum.FAIL);
        } catch (Exception e) {
            log.error("[用户重置密码异常]: ", e);
            return new BaseDTO(ResultEnum.FAIL);
        }
    }

}


## system
添加项目
```json5
{
  project:{
    name:'',
    remark:'',
  }
}
```

修改项目
```json5
{
  project:{
    id:'',
    name:'',
    remark:'',
  }
}
```

查询项目列表
```json5
{}
```
```json5
[
  {
    id:'',
    name:'',
    remark:'',
  },
]
```

删除项目
```json5
{
  project:{
    id:''
  }
}
```

添加api
```json5
{
  project:{
    id:'',
    name:''
  },
  api:{
    project:{},
    path:[],
    name:'',
    remark:'',
    access:false,// 权限控制，可选项
    service:false,// 业务逻辑处理，可选项
    checked:true,// 元数据校验，可选项
    metadata:{ 
      k:'v',
      k1:'v1',
      metadata:{
        k:'v',
      }
    }
  },
}
```

修改api
```json5
{
}
```
查询api列表
```json5
{

}
```

删除api
```json5
{

}
```





## invoke
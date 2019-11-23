$(function () {
    $("#userTable").bootstrapTable({
        url:"../sys/user/list",
        pagination:true,
        sidePagination:"server",
        pageSize:2,
        search:true,
        showRefresh:true,
        toolbar:"#toolbar",
        columns:[
            {checkbox:true},
            {title:"编号",field:"userId",sortable:true},
            {title:"用户名",field:"username"},
            {title:"邮箱",field:"email"},
            {title:"手机号",field:"mobile"},
            {title:"状态",field:"status"},
            // {title:"创建时间",field:"createTime"},
            {title:"创建时间",field:"createTime",formatter(value,row,index){
                //js 格式化毫秒时间
                return new Date(value).toLocaleString();

                }}

        ]
    });
});

var app=new Vue({
    el:"#userApp",
    data:{
        user:{},
        title:"",
        showUserList:true
    },
    methods:{
        add:function () {
            app.showUserList=false;
            app.title="新增用户";

        },
        update:function () {
            //判断用户是否选中其中一行
            var rows = $("#userTable").bootstrapTable("getSelections");
            if (rows.length == 0) {
                layer.alert("请选择一行");
                return;
            }
            if (rows.length > 1) {
                layer.alert("请选择一行修改");
                return;
            }
            //得到用户的id
            var row = rows[0];//得到选中的这一行
            var userId = row["userId"];//得到这一行的userId

            $.get("../sys/user/info/" + userId, function (r) {
                if (r.code == 0) {
                    app.showUserList = false;
                    app.title = "修改用户";
                    app.user = r.user;
                }

            });

        },
        del:function () {
            var rows = $("#userTable").bootstrapTable("getSelections");
            if (rows.length == 0) {
                layer.alert("请至少选择一行");
                return;
            }

            var ids=new Array();
            for (var i=0;i<rows.length;i++ ){
                ids.push(rows[i]["userId"])//得到第i行的userId
            }
            //得到确认框
            layer.confirm('您确定要删除所选数据吗？',{btn: ['确定','取消']},//可以无限个按钮
                function (index,layero) {

                $.ajax({
                    type:"post",
                    url: "../sys/user/del",
                    data: JSON.stringify(ids),
                    success:function (r) {
                        if (r.code==0){
                            layer.alert('删除成功');
                            $('#userTable').bootstrapTable('refresh');
                        }else{
                            layer.alert(r.msg);
                        }
                    },
                    error:function () {
                        layer.alert('服务器没有返回数据，可能服务器忙，请重试');
                    }
                });

            });

        },

        saveOrUpdate:function (event) {
            var userId=app.user.userId;
            //有user_id为 修改   无：新增
            var url=userId==null?"../sys/user/save":"../sys/user/update";
            $.post(url,JSON.stringify(app.user),function (r) {
                if (r.code==0){//处理成功
                    layer.alert('操作成功',function (index) {
                        layer.close(index);
                        app.reload();
                    });
                }else{
                    layer.alert(r.msg);
                }
            });

        },
        reload:function(){
            app.showUserList=true;
            $("#userTable").bootstrapTable("refresh");

        }

    }
});
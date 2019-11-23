var app=new Vue({

    el:"#assignRole",

    data:{
        users:[],
        roles:[]
    },
    methods:{
        getAllUsers:function () {

            $.get("/sys/user/userList",function (r) {
                app.users=r.userList;
                
            });

        },
        findUserNotHasRole:function () {

            var userId=$("#users option:checked").val();
            //alert(userId);
           // console.count(userId);
            $.get("../sys/user/notHasRole/"+userId,function (r) {
                //alert(r.roles);
                app.roles=r.roles;
            });
        },
        assignRole:function () {
            var userId=$("#users option:checked").val();
            var roleId=$("#hasRole option:checked").val();
            //alert(userId+"---"+roleId);
            // "../sys/user/addUserRole"+userId+roleId, 错误
            $.get("../sys/user/addUserRole/"+userId+"/"+roleId,function (r) {
                if(r.code==0){

                  // alert('添加成功');

                   app.findUserNotHasRole();

                }

            });



        }

    },

    created:function(){
        this.getAllUsers();
    }
})
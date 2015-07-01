<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script type="text/javascript">
        $(document).ready(function () {

            $('#userType').change(function(){
                var userType= $(this).val()
                console.log("userType"+userType);
                $.ajax({
                    url: "${createLink(controller: 'userDetail',action: 'getUserByType')}",
                    data:{type: userType},
                    success: function(data){
                        console.log("In side sucess");
                        $('#users').empty();
                        $('#users').html(data);
                    },
                    failure: function(data){
                        console.log("data===="+data)
                    }
                });
            });

        });
    </script>
    <meta name="layout" content="master">
    <g:set var="entityName" value="${message(code: 'label', default: 'Dashboard')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
    <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.7/css/jquery.dataTables.min.css">
    <script type="text/javascript" language="javascript"
            src="//cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js"></script>
    <style>
    p {
        text-align: center;
        font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
        font-size: 15px;
        font-weight: bold;
    }
    </style>
</head>

<body>
<div>
    <select id="userType">
        <option value="all">All Users</option>
        <option value="active">Active Users</option>
        <option value="inactive">Inactive Users</option>
    </select>
</div>
<div id="users">
<g:render template="listUserTable"></g:render>
    </div>
</body>

</html>
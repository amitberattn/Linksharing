<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#countTable').dataTable({
//                "bInfo": false
            });

            $(".statusChange").click(function(){
                var userId = $(this).attr('id');
                $.ajax({
                    url: "${createLink(controller: "userDetail",action: "changeUserStatus") }",
                    data: {id: userId},
                    success: function (data) {
                        if (data.statusFlag) {
                            $("#" + userId).html("Deactivate");
                            $("#active"+userId).html("Yes")
                            $("#"+userId).css('background-color','red')
                        }
                        else {
                            $("#" + userId).html("Activate");
                            $("#" + userId).html();
                            $("#active"+userId).html("No")
                            $("#"+userId).css('background-color','green')

                        }
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
<div id="dataTables">

    %{--<table id="countTable" class="table table-hover table-bordered table-striped">--}%
    <table id="countTable"  class="display">
        <thead>
        <tr>
            <p><g:sortableColumn property="id" title="${message(code: 'attribute.name.label', default: 'ID')}"/></p>

            <p><g:sortableColumn property="name"
                                 title="${message(code: 'attribute.name.label', default: 'Username')}"/></p>

            <p><g:sortableColumn property="email"
                                 title="${message(code: 'attribute.isEnabled.label', default: 'Email')}"/></p>

            <p><g:sortableColumn property="firstName"
                                 title="${message(code: 'attribute.isEnabled.label', default: 'First Name')}"/></p>

            <p><g:sortableColumn property="lastName"
                                 title="${message(code: 'attribute.isEnabled.label', default: 'Last Name')}"/></p>

            <p><g:sortableColumn property="Active"
                                 title="${message(code: 'attribute.isEnabled.label', default: 'Active')}"/></p>

            <p><g:sortableColumn property="Manage"
                                 title="${message(code: 'attribute.isEnabled.label', default: 'Manage')}"/></p>
        </tr>
        </thead>
        <tbody>
        <g:each in="${userDetailList}" status="i" var="userDetail">
            <tr>
                <td><p>
                    <g:link class="btn-link" action="profile" id="${userDetail.id}">
                        ${fieldValue(bean: userDetail, field: "id")}
                    </g:link>
                </p>
                </td>
                <td>
                    <p>
                        <g:link class="btn-link" action="profile" id="${userDetail.id}">
                            ${fieldValue(bean: userDetail, field: "username")}
                        </g:link>
                    </p>
                </td>
                <td><p>${userDetail.email}</p></td>
                <td><p>${userDetail.firstName}</p></td>
                <td><p>${userDetail.lastName}</p></td>
                <td><p id="active${userDetail.id}">${userDetail.active ? "Yes" : "No"}</p></td>
                <td><p style="cursor: pointer; display: block ; color: white; background-color: ${userDetail.active ? 'red':'darkgreen'};border-bottom: 1px black;border-radius: 14px;" class="statusChange" id="${userDetail.id}">${userDetail.active ? "Deactivate" : "Activate"}</p></td>
            </tr>
        </g:each>
        </tbody>
    </table>

    %{--    <div class="pagination right">
            <g:paginate total="${userDetailList.size()}"/>
        </ul>
        </div>--}%
</div>
</body>

</html>
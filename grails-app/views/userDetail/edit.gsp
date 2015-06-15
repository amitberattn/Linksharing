<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".markread").click(function () {
                var resourceId = $(this).attr('id');
                $.ajax({
                    url: "${createLink(controller: "readingItem",action: "markAsRead") }",
                    data: {id: resourceId},
                    success: function (data) {
                        if (data.isreadItem) {
                            $("#" + resourceId).html("Mark as unread");
                        }
                        else {
                            $("#" + resourceId).html("Mark as read");
                        }
                    }
                });
            });
        });
    </script>
    <meta name="layout" content="master">
    <g:set var="entityName" value="${message(code: 'label', default: 'Dashboard')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="widget-area-3 sidebar">
    <div class="widget kopa-article-list-widget">
        <g:render template="userInformation"/>
    </div>

    <div class="widget kopa-article-list-widget">
        <g:render template="my_subscription"/>
    </div><!--kopa-article-list-widget-->

    <div class="widget kopa-article-list-widget">
    </div><!--kopa-article-list-widget-->

</div><!--widget-area-3-->

<!--row-fluid-->

<div id="main-col">

    <div class="widget kopa-article-list-widget">
        <h3 class="widget-title"><span class="title-line"></span><span class="title-text">Update Profile</span></h3>
        <g:uploadForm useToken="true" class="clearfix reg-form" controller="userDetail" action="update"
                      name="contact-form" method="post">
            <g:render template="editForm"></g:render>
        </g:uploadForm>

    </div><!--kopa-article-list-widget-->
    <div class="widget kopa-article-list-widget">
        <h3 class="widget-title"><span class="title-line"></span><span class="title-text">Change Password</span></h3>
        <g:form controller="userDetail" action="changePassword">
            <g:render template="changePass"></g:render>
        </g:form>

    </div>
</div><!--main-col-->


<div class="clear"></div>

</body>
</html>
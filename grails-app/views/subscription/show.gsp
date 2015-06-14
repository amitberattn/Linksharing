<%@ page import="com.linksharing.Resource" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".markread").click(function () {
                console.log("id="+resourceId)
                var resourceId = $(this).attr('id');
                console.log("id="+resourceId)
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

            $(".resourceNo").click(function () {
                var topicId = $(this).attr('topicid')
                console.log(topicId)
                $.ajax({
                    url: "${createLink(controller: "subscription", action: "postDetails")}",
                    data:{id: topicId},
                    success: function (data) {
                        if (data) {
                            $('#post').html(data);
                        }
                    }
                });
            });
        });
    </script>
    <meta name="layout" content="master">
    %{--<g:set var="entityName" value="${message(code: 'label', default: 'Dashboard')}" />--}%
    %{--<title><g:message code="default.list.label" args="[entityName]" /></title>--}%
</head>

<body>
<div class="widget-area-3 sidebar">
    <div class="widget kopa-article-list-widget">
        <h3 class="widget-title"><span class="title-line"></span><span class="title-text">Subscription list</span></h3>
        <g:each in="${topicList}" status="i" var="topic">
            <g:render template="/topic/subscription" model="${[topicInstance: topic]}"/>
        </g:each>
    </div><!--kopa-article-list-widget-->

</div><!--widget-area-3-->


<div id="main-col">

    <div class="widget kopa-article-list-widget">
        <h3 class="widget-title"><span class="title-line"></span><span class="title-text">Posts</span></h3>

        <div id="post">
            <g:render template="/topic/post"
                      model="${[resourceList: topicList[1].resource as java.util.List<com.linksharing.Resource>]}"></g:render>
        </div></div><!--kopa-article-list-widget-->

</div><!--main-col-->


<div class="clear"></div>

</body>
</html>
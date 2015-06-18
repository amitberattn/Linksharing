<%@ page import="com.linksharing.Resource" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script type="text/javascript">
        $(document).ready(function () {

            var a = "${score}"
            var isRated = false
            console.log("score" + a)
            if (a == 0) {
                isRated = false

            } else {
                isRated = true
            }


            $('#star').raty({
                score: "${score}",
                readOnly: isRated,
                click: function (score, evt) {
                    var resourceId = $("#star").attr('rid')
                    console.log("rid=" + resourceId)
                    $.ajax({
                        url: "${createLink(controller: "resourceRating",action: "rating")}",
                        data: {id: resourceId, rate: score},
                        success: function (data) {
                            if (data.flag)
                                console.log("rating sucessful");
                        }
                    });
                }
            });


            $(".markread").click(function () {
                console.log("id=" + resourceId)
                var resourceId = $(this).attr('id');
                console.log("id=" + resourceId)
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
                    data: {id: topicId},
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
    <g:set var="entityName" value="${message(code: 'label', default: 'Dashboard')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
    <asset:stylesheet src="css/jquery.raty.css"/>
</head>

<body>
<asset:javascript src="js/jquery.raty.js"/>
<div class="widget-area-3 sidebar">
    <div class="widget kopa-article-list-widget">
        <h3 class="widget-title"><span class="title-line"></span><span class="title-text">Post</span></h3>
        <g:render template="post" model="${[resourceItem: resourceInstance]}"/>
    </div><!--kopa-article-list-widget-->

</div><!--widget-area-3-->

<div id="main-col">

    <div class="widget kopa-article-list-widget">
        <h3 class="widget-title"><span class="title-line"></span><span class="title-text">Treanding topics</span></h3>

        <div id="trendingTopicsDiv">
            <g:render template="trendingTopics"></g:render>
        </div>
    </div><!--kopa-article-list-widget-->

</div><!--main-col-->


<div class="clear"></div>

</body>
</html>
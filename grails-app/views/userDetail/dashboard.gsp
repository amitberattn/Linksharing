<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script type="text/javascript">
        $(document).ready(function(){
            $(".markread").click(function(){
                var resourceId = $(this).attr('id');
                $.ajax({
                    url: "${createLink(controller: "readingItem",action: "markAsRead") }",
                    data:{id:resourceId},
                    success: function(data){
                        if(data.isreadItem) {
                            $("#"+resourceId).html("Mark as unread");
                        }
                        else{
                            $("#"+resourceId).html("Mark as read");
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
        <h3 class="widget-title"><span class="title-line"></span><span class="title-text">Trending Topics</span></h3>
        <div class="tab-container-1">
            <ul>
                <g:render template="trendingTopics"></g:render>
            </ul>
        </div><!--tab-container-1-->
    </div><!--kopa-article-list-widget-->

</div><!--widget-area-3-->

<!--row-fluid-->

<div id="main-col">

    <div class="widget kopa-article-list-widget">
        <h3 class="widget-title"><span class="title-line"></span><span class="title-text">Inbox</span></h3>
       <g:render template="inbox"></g:render>

    </div><!--kopa-article-list-widget-->

</div><!--main-col-->


<div class="clear"></div>

</body>
</html>
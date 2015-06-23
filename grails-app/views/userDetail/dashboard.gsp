<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script type="text/javascript">
        $(document).ready(function () {


           $('#inboxSearchInput').keypress(function (e) {
                var key = e.which;
                if(key == 13)  // the enter key code
                {
                    console.log("enter press");
                    console.log($(this).val())
                    var searchText = $(this).val()

                    $.ajax({
                        url: "${createLink(controller: "userDetail",action: "searchInbox") }",
                        data: {txt:searchText},
                        success: function(resp){
                            $('#searchDiv').empty();
                            $('#searchDiv').html(resp);
                        }
                    });


                }
            });



//            chaange seriosness by ajax

            $('.seriousness').change(function(){
                var ser = $(this).val();
                var id = $(this).attr('id');
                $.ajax({
                    url: "${createLink(controller: "userDetail",action: "updateSeriousness") }",
                    data: {s:ser,id:id},
                    success: function(resp){
                        $("#"+id).val(ser)
                    }
                });
            });

// change privacy by ajax

            $('.visibility').change(function(){
                var visibility = $(this).val();
                var id = $(this).attr('id');
                $.ajax({
                    url: "${createLink(controller: "userDetail",action: "updateVisibility") }",
                    data: {visibility:visibility,id:id},
                    success: function(resp){
                        $("#"+id).val(visibility)
                    }
                });
            });



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
    <g:if test="${session.user.admin == false}">
        <div class="widget kopa-article-list-widget">
            <g:render template="my_subscription"/>
        </div><!--kopa-article-list-widget-->

        <div class="widget kopa-article-list-widget">
            <h3 class="widget-title"><span class="title-line"></span><span class="title-text">Trending Topics</span>
            </h3>

            <div class="tab-container-1">
                <div id="trendingTopicsDiv">
                    <g:render template="trendingTopics"></g:render>
                </div>
            </div><!--tab-container-1-->
        </div><!--kopa-article-list-widget-->
    </g:if>
    <g:else>
%{--             <div class="widget kopa-article-list-widget">
              <h3 class="widget-title"><span class="title-line"></span><span class="title-text">Topics</span></h3>
              <div class="tab-container-1">
                <div id="adminTopics">
                     <g:render template="topicsAdmin"></g:render>
                 </div>
              </div>--}%
        <div class="widget kopa-article-list-widget">
            <g:render template="my_subscription"/>
        </div><!--kopa-article-list-widget-->

        <div class="widget kopa-article-list-widget">
            <h3 class="widget-title"><span class="title-line"></span><span class="title-text">Topics</span>
            </h3>

            <div class="tab-container-1">
                <div id="adminTopics">
                    <g:render template="topicsAdmin"></g:render>
                </div>
            </div><!--tab-container-1-->
        </div>

    </g:else>
</div><!--widget-area-3-->

<!--row-fluid-->

    <div id="main-col">

        <div class="widget kopa-article-list-widget">
            <g:if test="${session.user.admin == false}">
                <h3 class="widget-title"><span class="title-text">Inbox</span>
                <span><g:textField name="inboxSearch" value="" placeholder="Search in inbox" style="float: right" id="inboxSearchInput"></g:textField></span></h3>
                <div id="searchDiv">
                <g:render template="inbox"></g:render>
                </div>
            </g:if>
            <g:else>
                <h3 class="widget-title"><span class="title-line"></span><span class="title-text">Post</span></h3>
                <g:render template="/topic/post"></g:render>
            </g:else>

        </div><!--kopa-article-list-widget-->

    </div><!--main-col-->


    <div class="clear"></div>

</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
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

        <div class="tab-container-1">
            <ul>
                <li>
                    <article class="entry-item clearfix">
                        <div class="entry-thumb"><a href="#"><asset:image src="placeholders/53x53/53x53-1.jpg"
                                                                          alt=""/></a></div>

                        <div class="entry-content">
                            <h4 class="entry-title"><a href="#">IPC Championships day-by-day guide</a></h4>

                            <p class="entry-description">Adrian Peterson exclusive interview Adrian Peterson exclusive interview Adrian Peterson exclusive interview</p>
                            <span class="entry-date">
                                <a href="#"><asset:image src="placeholders/facebook-icon.png" alt=""/></a>
                                <a href="#"><asset:image src="placeholders/Linkedin.png" alt=""/></a>
                                <a href="#"><asset:image src="placeholders/googleplus.png" alt=""/></a>

                                <div class="modify">
                                    <a href="#">View post</a>
                                    <a href="#">Mark as read</a>
                                    <a href="#">View full site</a>
                                    <a href="#">Download</a>
                                </div>
                            </span>
                        </div>
                    </article>
                </li>
                <li>
                    <article class="entry-item clearfix">
                        <div class="entry-thumb"><a href="#"><asset:image src="placeholders/53x53/53x53-1.jpg"
                                                                          alt=""/></a></div>

                        <div class="entry-content">
                            <h4 class="entry-title"><a href="#">IPC Championships day-by-day guide</a></h4>

                            <p class="entry-description">Adrian Peterson exclusive interview Adrian Peterson exclusive interview Adrian Peterson exclusive interview</p>
                            <span class="entry-date">
                                <a href="#"><asset:image src="placeholders/facebook-icon.png" alt=""/></a>
                                <a href="#"><asset:image src="placeholders/Linkedin.png" alt=""/></a>
                                <a href="#"><asset:image src="placeholders/googleplus.png" alt=""/></a>

                                <div class="modify">
                                    <a href="#">View post</a>
                                    <a href="#">Mark as read</a>
                                    <a href="#">View full site</a>
                                    <a href="#">Download</a>
                                </div>
                            </span>
                        </div>
                    </article>
                </li>
                <li>
                    <article class="entry-item clearfix">
                        <div class="entry-thumb"><a href="#"><asset:image src="placeholders/53x53/53x53-1.jpg"
                                                                          alt=""/></a></div>

                        <div class="entry-content">
                            <h4 class="entry-title"><a href="#">IPC Championships day-by-day guide</a></h4>

                            <p class="entry-description">Adrian Peterson exclusive interview Adrian Peterson exclusive interview Adrian Peterson exclusive interview</p>
                            <span class="entry-date">
                                <a href="#"><asset:image src="placeholders/facebook-icon.png" alt=""/></a>
                                <a href="#"><asset:image src="placeholders/Linkedin.png" alt=""/></a>
                                <a href="#"><asset:image src="placeholders/googleplus.png" alt=""/></a>

                                <div class="modify">
                                    <a href="#">View post</a>
                                    <a href="#">Mark as read</a>
                                    <a href="#">View full site</a>
                                    <a href="#">Download</a>
                                </div>
                            </span>
                        </div>
                    </article>
                </li>
                <li>
                    <article class="entry-item clearfix">
                        <div class="entry-thumb"><a href="#"><asset:image src="placeholders/53x53/53x53-1.jpg"
                                                                          alt=""/></a></div>

                        <div class="entry-content">
                            <h4 class="entry-title"><a href="#">IPC Championships day-by-day guide</a></h4>

                            <p class="entry-description">Adrian Peterson exclusive interview Adrian Peterson exclusive interview Adrian Peterson exclusive interview</p>
                            <span class="entry-date">
                                <a href="#"><asset:image src="placeholders/facebook-icon.png" alt=""/></a>
                                <a href="#"><asset:image src="placeholders/Linkedin.png" alt=""/></a>
                                <a href="#"><asset:image src="placeholders/googleplus.png" alt=""/></a>

                                <div class="modify">
                                    <a href="#">View post</a>
                                    <a href="#">Mark as read</a>
                                    <a href="#">View full site</a>
                                    <a href="#">Download</a>
                                </div>
                            </span>
                        </div>
                    </article>
                </li>
                <li>
                    <article class="entry-item clearfix">
                        <div class="entry-thumb"><a href="#"><asset:image src="placeholders/53x53/53x53-1.jpg"
                                                                          alt=""/></a></div>

                        <div class="entry-content">
                            <h4 class="entry-title"><a href="#">IPC Championships day-by-day guide</a></h4>

                            <p class="entry-description">Adrian Peterson exclusive interview Adrian Peterson exclusive interview Adrian Peterson exclusive interview</p>
                            <span class="entry-date">
                                <a href="#"><asset:image src="placeholders/facebook-icon.png" alt=""/></a>
                                <a href="#"><asset:image src="placeholders/Linkedin.png" alt=""/></a>
                                <a href="#"><asset:image src="placeholders/googleplus.png" alt=""/></a>

                                <div class="modify">
                                    <a href="#">View post</a>
                                    <a href="#">Mark as read</a>
                                    <a href="#">View full site</a>
                                    <a href="#">Download</a>
                                </div>
                            </span>
                        </div>
                    </article>
                </li>
            </ul>
        </div><!--tab-container-1-->
    </div><!--kopa-article-list-widget-->

</div><!--main-col-->


<div class="clear"></div>

</body>
</html>
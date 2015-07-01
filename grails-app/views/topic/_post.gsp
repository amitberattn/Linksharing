<div id="fb-root"></div>
<script>
    (function (d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) return;
        js = d.createElement(s);
        js.id = id;
        js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.3&appId=732451196865250";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));</script>

<div class="tab-container-1">
    <ul>
        <g:if test="${resourceList?.size() > 0}">
            <div id="postSelect" rid="${resourceList[0].id}"></div>
            <g:each in="${resourceList}" status="i" var="resourceItem">
                <li>
                    <article class="entry-item clearfix">
                        <div class="entry-thumb"><g:link controller="userDetail" action="profile"
                                                         id="${resourceItem.createdBy.id}"><img
                                    src="${resource(dir: 'images/profile', file: "${resourceItem.createdBy.username ?: 'user.png'}")}"
                                    alt=""/></g:link></div>

                        <div class="entry-content">
                            <p class="entry-description">${resourceItem.description}</p>
                            <span class="entry-date">
                                <g:if test="${resourceItem instanceof com.linksharing.LinkShare}">
                                    <div class="fb-share-button" data-href="${resourceItem.url}"
                                         data-layout="button_count"></div>
                                </g:if>
                                <g:else>
                                    <div class="fb-share-button"
                                         data-href="http://localhost:8080/linksharing/images/topic/${resourceItem.fileName}"
                                         data-layout="button_count"></div>
                                </g:else>
                                <a href="#"><asset:image src="placeholders/Linkedin.png" alt=""/></a>
                                <a href="#"><asset:image src="placeholders/googleplus.png" alt=""/></a>

                                <div class="modify">
                                    <g:link controller="resource" action="show"
                                            id="${resourceItem.id}">View post</g:link>
                                    <g:if test="${userDetail?.id in ((((resourceItem.readingItem as List).findAll {
                                        it.isRead == true
                                    }).userDetail as List).id as List<Long>)}">
                                        <a style="cursor: pointer" id="${resourceItem.id}"
                                           class="markread">Mark as unread</a>
                                    </g:if>
                                    <g:else>
                                        <a style="cursor: pointer" id="${resourceItem.id}"
                                           class="markread">Mark as read</a>
                                    </g:else>
                                    <g:if test="${resourceItem instanceof com.linksharing.LinkShare}">
                                        <a href="${resourceItem.url}" target="_blank">View full site</a>
                                    </g:if>
                                    <g:else>
                                    %{--<a href="/linksharing/images/topic/${resourceItem.fileName}">Download</a>--}%
                                        <g:link style="cursor: pointer" controller="documentResource" action="download"
                                                id="${resourceItem.id}">Download</g:link>
                                    </g:else>
                                </div>
                            </span>
                        </div>
                    </article>
                </li>
            </g:each>
        </g:if>
    </ul>
</div>
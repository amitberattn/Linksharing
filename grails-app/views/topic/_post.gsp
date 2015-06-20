<div class="tab-container-1">
    <ul>
        <g:if test="${resourceList.size() > 0}">
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
                                <a href="#"><asset:image src="placeholders/facebook-icon.png" alt=""/></a>
                                <a href="#"><asset:image src="placeholders/Linkedin.png" alt=""/></a>
                                <a href="#"><asset:image src="placeholders/googleplus.png" alt=""/></a>

                                <div class="modify">
                                    <g:link controller="resource" action="show"
                                            id="${resourceItem.id}">View post</g:link>
                                    <g:if test="${session.user.id in ((((resourceItem.readingItem as List).findAll {
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
<div class="tab-container-1">
    <ul>
        <g:if test="${documentResourceList.size() > 0}">
        <g:each in="${documentResourceList}" status="i" var="resourceItem">
            <li>
                <article class="entry-item clearfix">
                    <div class="entry-thumb"><a href="#"><img
                            src="${resource(dir: 'images/profile', file: "${resourceItem.createdBy.username ?: 'user.png'}")}"
                            alt=""/></a></div>

                    <div class="entry-content">
                        <p class="entry-description">${resourceItem.description}</p>
                        <span class="entry-date">
                            <a href="#"><asset:image src="placeholders/facebook-icon.png" alt=""/></a>
                            <a href="#"><asset:image src="placeholders/Linkedin.png" alt=""/></a>
                            <a href="#"><asset:image src="placeholders/googleplus.png" alt=""/></a>

                            <div class="modify">
                                <a href="#">View post</a>
                                <a href="#">Mark as read</a>
                                <a href="/linksharing/images/topic/${resourceItem.fileName}">Download</a>
                            </div>
                        </span>
                    </div>
                </article>
            </li>
        </g:each>
        </g:if>
        <g:if test="${linkShareList.size() > 0}">
        <g:each in="${linkShareList}" status="i" var="resourceItemLink">
            <li>
                <article class="entry-item clearfix">
                    <div class="entry-thumb"><a href="#"><img
                            src="${resource(dir: 'images/profile', file: "${resourceItemLink.createdBy.username ?: 'user.png'}")}"
                            alt=""/></a></div>

                    <div class="entry-content">
                        <p class="entry-description">${resourceItemLink.description}</p>
                        <span class="entry-date">
                            <a href="#"><asset:image src="placeholders/facebook-icon.png" alt=""/></a>
                            <a href="#"><asset:image src="placeholders/Linkedin.png" alt=""/></a>
                            <a href="#"><asset:image src="placeholders/googleplus.png" alt=""/></a>

                            <div class="modify">
                                <a href="#">View post</a>
                                <a href="#">Mark as read</a>
                                <a href="${resourceItemLink.url}">View full site</a>
                            </div>
                        </span>
                    </div>
                </article>
            </li>
        </g:each>
        </g:if>
    </ul>
</div>
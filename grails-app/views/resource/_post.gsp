<div class="tab-container-1">
    <ul>
        <li>
            <article class="entry-item clearfix">
                <div class="entry-thumb"><a href="#"><img
                        src="${resource(dir: 'images/profile', file: "${resourceItem.createdBy.username ?: 'user.png'}")}"
                        alt=""/></a></div>

                <div class="entry-content">
                    <p class="entry-description">${resourceItem.description}</p>
                    <p class="entry-description"><span class="star-rating" id="ratingspan" rid="${resourceItem.id}">
                        <input type="radio" name="rating" value="1" class="star1"><i></i>
                        <input type="radio" name="rating" value="2" class="star1"><i></i>
                        <input type="radio" name="rating" value="3" class="star1"><i></i>
                        <input type="radio" name="rating" value="4" class="star1"><i></i>
                        <input type="radio" name="rating" value="5" class="star1"><i></i>
                    </span></p>
                    <span class="entry-date">
                        <a href="#"><asset:image src="placeholders/facebook-icon.png" alt=""/></a>
                        <a href="#"><asset:image src="placeholders/Linkedin.png" alt=""/></a>
                        <a href="#"><asset:image src="placeholders/googleplus.png" alt=""/></a>

                        <div class="modify">
                        %{--<a href="#">View post</a>--}%
                            <g:link controller="resource" action="show" id="${resourceItem.id}"
                                    style="cursor: pointer">View post</g:link>
                            <g:if test="${session.user.id in ((((resourceItem.readingItem as List).findAll {
                                it.isRead == true
                            }).userDetail as List).id as List<Long>)}">
                                <a style="cursor: pointer" id="${resourceItem.id}" class="markread">Mark as unread</a>
                            </g:if>
                            <g:else>
                                <a style="cursor: pointer" id="${resourceItem.id}" class="markread">Mark as read</a>
                            </g:else>
                            <g:if test="${resourceItem instanceof com.linksharing.LinkShare}">
                                <a href="${resourceItem.url}">View full site</a>
                            </g:if>
                            <g:else>
                                <a href="/linksharing/images/topic/${resourceItem.fileName}">Download</a>
                            </g:else>
                        </div>
                    </span>
                </div>
            </article>
        </li>
    </ul>
</div>
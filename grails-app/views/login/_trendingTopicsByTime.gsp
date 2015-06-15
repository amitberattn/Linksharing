<ul>
    <g:each in="${todyTopicList}" var="topic">
    <li>
        <article class="entry-item clearfix">
            <div class="entry-thumb"><a href="#"><a href="#"><img
                    src="${resource(dir: 'images/profile', file: "${topic.createdBy.username ?: 'user.png'}")}"
                    alt=""/></a>
            </div>

            <p class="entry-description">
                <b>${topic.createdBy.firstName} ${topic.createdBy.lastName}</b>
                &nbsp;&nbsp;
                <i>${topic.createdBy.username}</i>
                &nbsp;&nbsp;
                <span class="entry-date">${topic.createdBy.dateCreated}</span>
                &nbsp;&nbsp;
                <a href="#" style="float: right">${topic.name}</a>
            </p>

            %{--<p class="entry-description">Adrian Peterson exclusive interview Adrian Peterson exclusive interview Adrian Peterson exclusive interview</p>--}%
            <span class="entry-date">
                <a href="#"><asset:image src="placeholders/facebook-icon.png"/></a>
                <a href="#"><asset:image src="placeholders/Linkedin.png" alt=""/></a>
                <a href="#"><asset:image src="placeholders/googleplus.png" alt=""/></a>

                <div class="modify">
                    <a href="#">View post</a>
                </div>
            </span>
        </article>
    </li>
    </g:each>
</ul>
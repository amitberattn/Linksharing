<ul>
    <g:each in="${todyTopicList}" var="topic">
    <li>
        <article class="entry-item clearfix">
            <div class="entry-thumb"><g:link controller="userDetail" action="profile" id="${topic.createdBy.id}" style="cursor: pointer"><img
                    src="${resource(dir: 'images/profile', file: "${topic.createdBy.username ?: 'user.png'}")}"
                    alt=""/></g:link>
            </div>

            <p class="entry-description">
                <b>${topic.createdBy.firstName} ${topic.createdBy.lastName}</b>
                &nbsp;&nbsp;
                <i>${topic.createdBy.username}</i>
                &nbsp;&nbsp;
                %{--<span class="entry-date">${topic.dateCreated}</span>--}%
                <span class="entry-date"><g:formatDate format="dd-MMM-yyyy HH:mm" date="${topic.dateCreated}"/></span>
                &nbsp;&nbsp;
                <g:link controller="topic" action="show" id="${topic.id}" style="float: right"> ${topic.name}</g:link>
            </p>

            %{--<p class="entry-description">Adrian Peterson exclusive interview Adrian Peterson exclusive interview Adrian Peterson exclusive interview</p>--}%
            <span class="entry-date">
                <a href="#"><asset:image src="placeholders/facebook-icon.png"/></a>
                <a href="#"><asset:image src="placeholders/Linkedin.png" alt=""/></a>
                <a href="#"><asset:image src="placeholders/googleplus.png" alt=""/></a>

                <div class="modify">
                </div>
            </span>
        </article>
    </li>
    </g:each>
</ul>
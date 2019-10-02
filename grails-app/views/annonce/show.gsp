<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'annonce.label', default: 'Annonce')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-annonce" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-annonce" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>

                <ol class="property-list annonce">

                    <li class="fieldcontain">
                        <span id="title-label" class="property-label">Title</span>
                        <div class="property-value" aria-labelledby="title-label">test</div>
                    </li>

                    <li class="fieldcontain">
                        <span id="description-label" class="property-label">Description</span>
                        <div class="property-value" aria-labelledby="description-label">test</div>
                    </li>

                    <li class="fieldcontain">
                        <span id="validTill-label" class="property-label">Valid Till</span>
                        <div class="property-value" aria-labelledby="validTill-label">2019-10-01 00:00:00 CEST</div>
                    </li>

                    <li class="fieldcontain">
                        <span id="illustration-label" class="property-label">Illustration</span>
                        <div class="property-value" aria-labelledby="illustration-label"><ul><li><a href="/illustration/show/17">com.mbds.annonces.Illustration : 17</a></li></ul></div>
                    </li>

                    <li class="fieldcontain">
                        <span id="state-label" class="property-label">State</span>
                        <div class="property-value" aria-labelledby="state-label">False</div>
                    </li>

                    <li class="fieldcontain">
                        <span id="author-label" class="property-label">Author</span>
                        <div class="property-value" aria-labelledby="author-label"><a href="/user/show/1">username</a></div>
                    </li>
            <g:form resource="${this.annonce}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.annonce}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>

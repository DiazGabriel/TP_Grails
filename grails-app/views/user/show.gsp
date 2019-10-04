<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-user" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>

            <form action="save" method="POST" enctype="multipart/form-data">

                <fieldset class="form">
                    <div id="show">
                        <div class='fieldcontain'>
                            <label>User name:</label>${user.username}
                        </div>
                        <div class='fieldcontain'>
                            <label>Password:</label>**********
                        </div>
                        <div class="fieldcontain">
                            <span id="thumbnail-label" class="property-label">Thumbnail:</span>
                            <div class="property-value" aria-labelledby="thumbnail-label">
                                <ul>
                                    <g:each in="${user.thumbnail}" var="illustration">
                                        <img width="100" height="100" src="http://localhost:8080/assets/${illustration.filename}"/>
                                    </g:each>
                                </ul></div>
                        </div>
                        <div class="fieldcontain">
                            <span id="annonce-label" class="property-label">Annonce:</span>
                            <div class="property-value" aria-labelledby="annonce-label">
                                <ul>
                                    <g:each in="${user.annonces}" var="annonce">
                                        <li>${annonce.title}</li>
                                    </g:each>
                                </ul></div>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
            <g:form resource="${this.user}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.user}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
    </body>
</html>




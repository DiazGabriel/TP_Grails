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

            <form action="save" method="POST" enctype="multipart/form-data">

                <fieldset class="form">
                <div id="show">
                    <div class='fieldcontain'>
                        <label>Title:</label>${annonce.title}
                    </div>
                    <div class='fieldcontain'>
                        <label>Description:</label>${annonce.description}
                    </div>
                    <div class='fieldcontain'>
                        <label>validTill:</label><g:formatDate format="dd MMM yyyy" date="${annonce.validTill}"/>
                    </div>
                    <div class="fieldcontain">
                        <span id="illustrations-label" class="property-label">Illustrations</span>
                        <div class="property-value" aria-labelledby="illustrations-label">
                            <ul>
                                <g:each in="${annonce.illustration}" var="illustration">
                                    <li><img width="100" height="100" src="http://localhost:8080/assets/${illustration.filename}"/></li>
                                </g:each>
                            </ul></div>
                    </div>
                    <div class='fieldcontain'>
                        <label>State:</label>${annonce.state}
                    </div>
                    <div class='fieldcontain'>
                        <label>Author:</label>${annonce.author}
                    </div>
                </div>
                </fieldset>
            <g:form resource="${this.annonce}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.annonce}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
            </form>
        </div>
    </body>
</html>

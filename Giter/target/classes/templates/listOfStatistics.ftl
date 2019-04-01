<#import "parts/common.ftl" as c>

<@c.page>

<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Find new user
</a>
<div class="collapse" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" action="/findNew">
            <div class="form-group">
                <input type="text" class="form-control" name="login" placeholder="Type in login" />
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Find</button>
            </div>
        </form>
    </div>
</div>
<div class="card-columns">
    <#list statistics as statistic>
    <div class="card my-3">
        <div class="m-2">
            <span>${statistic.login}</span>
        </div>
        <div class="m-2">
        <#if statistic.email??>
            <span>${statistic.email}</span>
        </#if>
        </div>
        <div class="m-2">
            <span>Number of repositories: ${statistic.numberOfRepositories}</span>
        </div>
        <div class="m-2">
            <span>Number of languages used: ${statistic.numberOfLanguagesUsed}</span>
        </div>
        <div class="card-footer text-muted">
            <a href="/find/repos/${statistic.login}"><span>Repositories</span></a>
        </div>
        <div class="card-footer text-muted">
            <a href="/find/languages/${statistic.login}"><span>Languages</span></a>
        </div>
        <div class="card-footer text-muted">
            <a href="/delete/${statistic.login}"><span>Delete</span></a>
        </div>
    </div>
    <#else>
    No users
    </#list>
</div>
</@c.page>

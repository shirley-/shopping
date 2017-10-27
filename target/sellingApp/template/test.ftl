<#--
<#if Request["user"]?exists >
	${user.username}
	request: ${Request["user"]["username"]}

</#if>
<#if Session["user"]?exists >
	session: ${Session["user"]}
</#if>
-->

<#if !productList?has_content>
<div class="n-result">
    <h3>暂无内容！</h3>
</div>
</#if>
<#list productList as x>
	${x.id}
</#list>

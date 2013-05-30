<%@ page import="au.org.emii.portal.Metadata"%>
<%@ page import="au.org.emii.portal.Publication"%>
<!doctype html>
<div class="p-centre">
	<div class="p-centre-item" style="width: 520px">
		<h1>RIF-CS Metadata</h1>
		<g:form action="save">
			<div class="dialog">
				<table>
					<tbody>
						<g:render template="form"></g:render>
					</tbody>
				</table>
			</div>
			<div class="buttons">
				<span class="button"><input type="button" name="save" class="save"
						value="${message(code: 'default.button.save.label', default: 'Save')}"
						onClick="validate(this.form);" /></span>
			</div>
		</g:form>

        <div class="spacer floatLeft homePanelWidget"  style="width:370px">
            ${ cfg.footerContent }
        </div>
		<div class="clear footer">
			${ portalBuildInfo }
		</div>
	</div>
</div>

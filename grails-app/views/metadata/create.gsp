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
				<span class="button"><g:submitButton name="create"
						class="save"
						value="${message(code: 'default.button.create.label', default: 'Save')}" /></span>
			</div>
		</g:form>

		<div class="clear spacer"></div>
		<div class="spacer clear footer">
			<div class="spacer floatLeft homePanelWidget" style="width: 100px">
				<img class="minispacer" src="images/DIISRTE-stacked-2012.png"
					alt="DIISTRE logo" />
			</div>
			<div class="spacer floatLeft homePanelWidget" style="width: 370px">
				%{--${ cfg.footerContent }--}%
			</div>
		</div>
		<div class="clear footer">
			${ portalBuildInfo }
		</div>
	</div>
</div>

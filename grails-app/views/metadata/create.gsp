<%@ page import="au.org.emii.portal.Metadata" %>
<!doctype html>
<div class="p-centre">
	<div class="p-centre-item" style="width:520px">
	<h1>Create Metadata</h1>
	<form>
		<label>Collection Period From</label>
		<input type="date" name="collectionPeriodFrom" />
		<label>Embargo?</label>
		<input type="checkbox" name="embargo" />
	</form>

    <div class="clear spacer"></div>
    <div class="spacer clear footer">
        <div class="spacer floatLeft homePanelWidget"  style="width:100px">
            <img class="minispacer" src="images/DIISRTE-stacked-2012.png" alt="DIISTRE logo"/>
        </div>
        <div class="spacer floatLeft homePanelWidget"  style="width:370px">
            ${ cfg.footerContent }
        </div>
    </div>
    <div class="clear footer"> ${ portalBuildInfo }</div>
  </div>
</div>

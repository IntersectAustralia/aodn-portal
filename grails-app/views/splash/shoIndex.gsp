
<%--

 Copyright 2012 IMOS

 The AODN/IMOS Portal is distributed under the terms of the GNU General Public License

--%>


<div class="p-centre">
	<div class="p-centre-item" style="width:520px">
        <h1>Welcome to the Sydney Harbour Observatory</h1>

        %{--<p>This portal is the primary access point for search, discovery, access and download of data collected by the Australian marine community. Primary datasets are contributed by the <a  target="_blank" class="external"  title="IMOS home page" href="http://www.imos.org.au" >Integrated Marine Observing System (IMOS)</a> an Australian Government Research Infrastructure project, and the six Commonwealth Agencies with responsibilities in the Australian marine jurisdiction (Australian Antarctic Division, Australian Institute for Marine Science, Bureau of Meteorology, Commonwealth Science and Industrial Research Organisation, Geoscience Australia and the Royal Australian Navy). You can obtain full information about the AODN via the <a title="AODN home page" class="external" target="_blank"  href="http://www.imos.org.au/aodn.html" >AODN Webpage</a>.</p>--}%
        <p>The Sydney Harbour Observatory (SHO) portal displays real-time data for Sydney Harbour and its surrounding estuaries using a hydrodynamic model. The model collates observations from the Bureau of Meteorology, Macquarie University, Sydney Ports Authority and the Manly Hydraulics Laboratory offshore buoy. SHO also acts as a data management tool to manage and archive input from researchers centrally, with descriptions of this data being made discoverable through the Research Data Australia web portal. The University of Sydney and the Sydney Institute of Marine Science are responsible for the innovative developments of SHO.</p>

        <h3>The portal provides two ways of discovering data:</h3>

        <div class="floatLeft">Either through our <a href="" onClick="setViewPortTab(1);return false;" >map</a> interface,<BR>or by searching our <a href="" onClick="setViewPortTab(2);return false;" >metadata catalogue</a>.</div>

        <div id="viewPortHomepageLinks" class="floatLeft">
          <div  class="viewPortLinksBackground viewPortLinks"><a onclick="setViewPortTab(1);return false;" href="">Map</a></div>
          <div  class="viewPortLinksBackground viewPortLinks"><a onclick="setViewPortTab(2);return false;" href="">Search</a></div>
        </div>

        <div class="clear spacer"></div>

        <h3>Affiliated Institutions</h3>

        <div class="spacer floatLeft homePanelWidget"  style="width:230px">
            <img src="${createLinkTo(dir: 'images', file: 'sims_logo.png')}" />

            <p>
            <img src="${createLinkTo(dir: 'images', file: 'ands_logo.png')}" />

            <p>
            <img src="${createLinkTo(dir: 'images', file: 'mq_logo.png')}" />

            <p>
            <img src="${createLinkTo(dir: 'images', file: 'nsw-public-works.png')}" />

            <p>
            <img src="${createLinkTo(dir: 'images', file: 'nsw-enviro.png')}" />

            <p>
            <img src="${createLinkTo(dir: 'images', file: 'DSI_Logo.png')}" />

            <p>
            <img src="${createLinkTo(dir: 'images', file: 'Intersect.png')}" />
        </div>

        <div class="spacer floatLeft homePanelWidget"  style="width:370px">
            ${ cfg.footerContent }
        </div>
    </div>
</div>

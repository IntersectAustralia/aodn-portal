package au.org.emii.portal

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import grails.converters.JSON;

class MenuItem implements Comparable<MenuItem> {
	
	Long layerId
	Long serverId
	Boolean leaf
	String text
	Integer menuPosition
	Integer parentPosition
	SortedSet childItems
	Layer layer
	Server server
	
	// This is only here for the purpose the migration and should be removed
	// afterwards
	Long parentId
	
    static constraints = {
		layerId(nullable: true)
		serverId(nullable: true)
		parentPosition(nullable: true)
		menu(nullable: true)
    }
	
	static belongsTo = [parent: MenuItem, menu: Menu]
	static hasMany = [childItems: MenuItem]
	
	static mapping = {
		layer updateable: false
		layer insertable: false
		server updateable: false
		server insertable: false
		childItems cascade: 'all-delete-orphan'
		parentId updateable: false
		parentId insertable: false
	}
	
	MenuItem() {
		childItems = [] as SortedSet
	}
	
	int compareTo(MenuItem other) {
		return new CompareToBuilder()
			.append(menuPosition, other.menuPosition)
			.append(parentPosition, other.parentPosition)
			.append(id, other.id)
			.toComparison();
	}
	
	boolean equals(Object o) {
		if (is(o)) {
			return true
		}
		if (!(o instanceof MenuItem)) {
			return false
		}
		
		MenuItem rhs = (MenuItem)o
		return new EqualsBuilder()
			.append(id, rhs.id)
			.isEquals()
	}
	
	@Override
	String toString() {
		return text
	}
	
	def parseJson(json, menuPosition) {
		parseJson(json, menuPosition, null)
	}
	
	def parseJson(json, menuPosition, parentPosition) {
		def itemsJsonArray = JSON.use("deep") {
			JSON.parse(json)
		}
		text = itemsJsonArray.text
		layerId = itemsJsonArray.grailsLayerId?.toLong()
		serverId = itemsJsonArray.grailsServerId?.toLong()
		leaf = itemsJsonArray.leaf?.toBoolean()
		this.menuPosition = menuPosition
		this.parentPosition = parentPosition
		
		if (itemsJsonArray.children) {
			_parseChildren(itemsJsonArray.children.toString())
		}
		else if (itemsJsonArray.json) {
			_parseChildren(itemsJsonArray.json.toString())
		}
	}
	
	def getBaseLayers() {
		def baseLayers = []
		if (layer.isBaseLayer) {
			baseLayers << layer
		}
		getChildItems().each { item ->
			baseLayers.addAll(item.getBaseLayers())
		}
		return baseLayers
	}
	
	def _parseChildren(json) {
		def itemsJsonArray = JSON.use("deep") {
			JSON.parse(json)
		}
		
		def tmpItems = [] as Set
		
		itemsJsonArray.eachWithIndex { item, index ->
			def menuItem = _findItem(item.id)
			menuItem.parseJson(item.toString(), menuPosition, index)
			tmpItems << menuItem
			if (!menuItem.id) {
				addToChildItems(menuItem)
			}
		}
		_purge(tmpItems)
	}
	
	def _findItem(id) {
		def item
		if (id && !getChildItems().isEmpty()) {
			item = getChildItems().find { it.id == id }
		}
		return item ?: new MenuItem()
	}
	
	def _purge(keepers) {
		def discards = [] as Set
		getChildItems().each { item ->
			if (!keepers.contains(item)) {
				discards << item
			}
		}
		discards.each { item ->
			removeFromChildItems(item)
		}
	}
}
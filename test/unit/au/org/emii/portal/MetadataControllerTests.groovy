package au.org.emii.portal



import org.junit.*
import grails.test.mixin.*

@TestFor(MetadataController)
@Mock(Metadata)
class MetadataControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/metadata/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.metadataInstanceList.size() == 0
        assert model.metadataInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.metadataInstance != null
    }

    void testSave() {
        controller.save()

        assert model.metadataInstance != null
        assert view == '/metadata/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/metadata/show/1'
        assert controller.flash.message != null
        assert Metadata.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/metadata/list'

        populateValidParams(params)
        def metadata = new Metadata(params)

        assert metadata.save() != null

        params.id = metadata.id

        def model = controller.show()

        assert model.metadataInstance == metadata
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/metadata/list'

        populateValidParams(params)
        def metadata = new Metadata(params)

        assert metadata.save() != null

        params.id = metadata.id

        def model = controller.edit()

        assert model.metadataInstance == metadata
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/metadata/list'

        response.reset()

        populateValidParams(params)
        def metadata = new Metadata(params)

        assert metadata.save() != null

        // test invalid parameters in update
        params.id = metadata.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/metadata/edit"
        assert model.metadataInstance != null

        metadata.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/metadata/show/$metadata.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        metadata.clearErrors()

        populateValidParams(params)
        params.id = metadata.id
        params.version = -1
        controller.update()

        assert view == "/metadata/edit"
        assert model.metadataInstance != null
        assert model.metadataInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/metadata/list'

        response.reset()

        populateValidParams(params)
        def metadata = new Metadata(params)

        assert metadata.save() != null
        assert Metadata.count() == 1

        params.id = metadata.id

        controller.delete()

        assert Metadata.count() == 0
        assert Metadata.get(metadata.id) == null
        assert response.redirectedUrl == '/metadata/list'
    }
}

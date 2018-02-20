package pl.hypeapp.dataproviders.entity.mapper

abstract class Mapper<Out, In> {

    abstract fun transform(item: In?): Out?

    fun transform(collection: Collection<In>): List<Out> {
        val list = ArrayList<Out>()
        var model: Out?
        for (entity in collection) {
            model = transform(entity)
            if (model != null) {
                list.add(model)
            }
        }
        return list
    }
}

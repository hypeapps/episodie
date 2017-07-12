package pl.hypeapp.dataproviders.entity.mapper

import java.util.*

abstract class EntityMapper<Model, Entity> {

    abstract fun transform(entity: Entity?): Model?

    fun transform(collection: Collection<Entity>): List<Model> {
        val list = ArrayList<Model>()
        var model: Model?
        for (entity in collection) {
            model = transform(entity)
            if (model != null) {
                list.add(model)
            }
        }
        return list
    }
}
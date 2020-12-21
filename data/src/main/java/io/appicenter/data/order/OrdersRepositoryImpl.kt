package io.appicenter.data.order

import io.appicenter.data.order.adapters.toData
import io.appicenter.data.order.adapters.toDomain
import io.appicenter.data.order.db.OrdersDao
import io.appicenter.data.utils.transform
import io.appicenter.domain.model.Order
import io.appicenter.domain.repository.OrdersRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class OrdersRepositoryImpl(private val ordersDao: OrdersDao) : OrdersRepository {
    override fun add(order: Order): Completable {
        return ordersDao.add(order.toData())
    }

    override fun get(): Flowable<List<Order>> {
        return ordersDao.getAll()
            .map { dataOrders -> dataOrders.transform { it.toDomain() } }
    }

    override fun update(order: Order): Completable {
        return ordersDao.update(order.toData())
    }

    override fun delete(order: Order): Completable {
        return ordersDao.delete(order.toData())
    }

    override fun deleteAll(): Completable {
        return ordersDao.deleteAll()
    }


}
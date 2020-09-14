package com.cabify.data.order

import com.cabify.data.order.adapters.toData
import com.cabify.data.order.adapters.toDomain
import com.cabify.data.order.db.OrdersDao
import com.cabify.data.utils.transform
import com.cabify.domain.model.Order
import com.cabify.domain.repository.OrdersRepository
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
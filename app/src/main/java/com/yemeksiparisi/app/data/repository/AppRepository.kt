package com.yemeksiparisi.app.data.repository

import androidx.lifecycle.LiveData
import com.yemeksiparisi.app.data.dao.*
import com.yemeksiparisi.app.data.entity.*

class AppRepository(
    private val personDao: PersonDao,
    private val menuItemDao: MenuItemDao,
    private val groupDao: GroupDao,
    private val groupMemberDao: GroupMemberDao,
    private val orderItemDao: OrderItemDao
) {
    // Person operations
    fun getAllPersons(): LiveData<List<Person>> = personDao.getAllPersons()
    suspend fun insertPerson(person: Person) = personDao.insertPerson(person)
    suspend fun updatePerson(person: Person) = personDao.updatePerson(person)
    suspend fun deletePerson(person: Person) = personDao.deletePerson(person)
    suspend fun getPersonById(id: Int) = personDao.getPersonById(id)

    // MenuItem operations
    fun getAllMenuItems(): LiveData<List<MenuItem>> = menuItemDao.getAllMenuItems()
    fun getMenuItemsByCategory(category: String): LiveData<List<MenuItem>> = 
        menuItemDao.getMenuItemsByCategory(category)
    fun getAllCategories(): LiveData<List<String>> = menuItemDao.getAllCategories()
    suspend fun insertMenuItem(menuItem: MenuItem) = menuItemDao.insertMenuItem(menuItem)
    suspend fun updateMenuItem(menuItem: MenuItem) = menuItemDao.updateMenuItem(menuItem)
    suspend fun deleteMenuItem(menuItem: MenuItem) = menuItemDao.deleteMenuItem(menuItem)
    suspend fun getMenuItemById(id: Int) = menuItemDao.getMenuItemById(id)

    // Group operations
    fun getAllGroups(): LiveData<List<Group>> = groupDao.getAllGroups()
    suspend fun insertGroup(group: Group) = groupDao.insertGroup(group)
    suspend fun updateGroup(group: Group) = groupDao.updateGroup(group)
    suspend fun deleteGroup(group: Group) = groupDao.deleteGroup(group)
    suspend fun getGroupById(id: Int) = groupDao.getGroupById(id)

    // GroupMember operations
    fun getGroupMembers(groupId: Int): LiveData<List<GroupMember>> = 
        groupMemberDao.getGroupMembers(groupId)
    suspend fun insertGroupMember(groupMember: GroupMember) = 
        groupMemberDao.insertGroupMember(groupMember)
    suspend fun deleteGroupMember(groupMember: GroupMember) = 
        groupMemberDao.deleteGroupMember(groupMember)
    suspend fun getGroupMember(groupId: Int, personId: Int) = 
        groupMemberDao.getGroupMember(groupId, personId)

    // OrderItem operations
    fun getGroupOrderItems(groupId: Int): LiveData<List<OrderItem>> = 
        orderItemDao.getGroupOrderItems(groupId)
    fun getPersonOrderItems(groupId: Int, personId: Int): LiveData<List<OrderItem>> = 
        orderItemDao.getPersonOrderItems(groupId, personId)
    suspend fun insertOrderItem(orderItem: OrderItem) = orderItemDao.insertOrderItem(orderItem)
    suspend fun updateOrderItem(orderItem: OrderItem) = orderItemDao.updateOrderItem(orderItem)
    suspend fun deleteOrderItem(orderItem: OrderItem) = orderItemDao.deleteOrderItem(orderItem)
    fun getPersonalTotal(groupId: Int, personId: Int): LiveData<Double?> = 
        orderItemDao.getPersonalTotal(groupId, personId)
    fun getGroupTotal(groupId: Int): LiveData<Double?> = orderItemDao.getGroupTotal(groupId)
    suspend fun deleteGroupOrderItems(groupId: Int) = 
        orderItemDao.deleteGroupOrderItems(groupId)
}

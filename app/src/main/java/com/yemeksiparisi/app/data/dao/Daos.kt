package com.yemeksiparisi.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yemeksiparisi.app.data.entity.*

@Dao
interface PersonDao {
    @Insert
    suspend fun insertPerson(person: Person): Long

    @Update
    suspend fun updatePerson(person: Person)

    @Delete
    suspend fun deletePerson(person: Person)

    @Query("SELECT * FROM persons ORDER BY name ASC")
    fun getAllPersons(): LiveData<List<Person>>

    @Query("SELECT * FROM persons WHERE id = :id")
    suspend fun getPersonById(id: Int): Person?
}

@Dao
interface MenuItemDao {
    @Insert
    suspend fun insertMenuItem(menuItem: MenuItem): Long

    @Update
    suspend fun updateMenuItem(menuItem: MenuItem)

    @Delete
    suspend fun deleteMenuItem(menuItem: MenuItem)

    @Query("SELECT * FROM menu_items ORDER BY category, name ASC")
    fun getAllMenuItems(): LiveData<List<MenuItem>>

    @Query("SELECT * FROM menu_items WHERE category = :category ORDER BY name ASC")
    fun getMenuItemsByCategory(category: String): LiveData<List<MenuItem>>

    @Query("SELECT DISTINCT category FROM menu_items ORDER BY category ASC")
    fun getAllCategories(): LiveData<List<String>>

    @Query("SELECT * FROM menu_items WHERE id = :id")
    suspend fun getMenuItemById(id: Int): MenuItem?
}

@Dao
interface GroupDao {
    @Insert
    suspend fun insertGroup(group: Group): Long

    @Update
    suspend fun updateGroup(group: Group)

    @Delete
    suspend fun deleteGroup(group: Group)

    @Query("SELECT * FROM groups ORDER BY createdAt DESC")
    fun getAllGroups(): LiveData<List<Group>>

    @Query("SELECT * FROM groups WHERE id = :id")
    suspend fun getGroupById(id: Int): Group?
}

@Dao
interface GroupMemberDao {
    @Insert
    suspend fun insertGroupMember(groupMember: GroupMember): Long

    @Delete
    suspend fun deleteGroupMember(groupMember: GroupMember)

    @Query("SELECT * FROM group_members WHERE groupId = :groupId")
    fun getGroupMembers(groupId: Int): LiveData<List<GroupMember>>

    @Query("SELECT * FROM group_members WHERE groupId = :groupId AND personId = :personId")
    suspend fun getGroupMember(groupId: Int, personId: Int): GroupMember?
}

@Dao
interface OrderItemDao {
    @Insert
    suspend fun insertOrderItem(orderItem: OrderItem): Long

    @Update
    suspend fun updateOrderItem(orderItem: OrderItem)

    @Delete
    suspend fun deleteOrderItem(orderItem: OrderItem)

    @Query("SELECT * FROM order_items WHERE groupId = :groupId ORDER BY createdAt DESC")
    fun getGroupOrderItems(groupId: Int): LiveData<List<OrderItem>>

    @Query("SELECT * FROM order_items WHERE groupId = :groupId AND personId = :personId")
    fun getPersonOrderItems(groupId: Int, personId: Int): LiveData<List<OrderItem>>

    @Query("SELECT * FROM order_items WHERE id = :id")
    suspend fun getOrderItemById(id: Int): OrderItem?

    @Query("SELECT SUM(CASE WHEN isShared = 0 THEN quantity * itemPrice ELSE 0 END) FROM order_items WHERE groupId = :groupId AND personId = :personId")
    fun getPersonalTotal(groupId: Int, personId: Int): LiveData<Double?>

    @Query("SELECT SUM(quantity * itemPrice) FROM order_items WHERE groupId = :groupId")
    fun getGroupTotal(groupId: Int): LiveData<Double?>

    @Query("DELETE FROM order_items WHERE groupId = :groupId")
    suspend fun deleteGroupOrderItems(groupId: Int)
}

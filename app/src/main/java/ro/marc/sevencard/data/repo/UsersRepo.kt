package ro.marc.sevencard.data.repo

import kotlinx.coroutines.flow.Flow
import ro.marc.sevencard.data.User

interface UsersRepo {

    fun getAll(): Flow<List<User>>

    fun save(user: User): Flow<Long>

    fun getById(id: Long): Flow<User?>

}

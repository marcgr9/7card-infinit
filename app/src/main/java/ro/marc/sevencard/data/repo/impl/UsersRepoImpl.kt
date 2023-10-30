package ro.marc.sevencard.data.repo.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ro.marc.sevencard.data.User
import ro.marc.sevencard.data.local.UserDAO
import ro.marc.sevencard.data.local.UserEntity
import ro.marc.sevencard.data.repo.UsersRepo

class UsersRepoImpl(
    private val userDAO: UserDAO,
): UsersRepo {

    override fun getAll() = flow {
        emit(
            userDAO.getAll().map {
                User(it.id, it.alias)
            }
        )
    }

    override fun getById(id: Long) = flow {
        val result = userDAO.getById(id)
        if (result != null) {
            emit(User(result.id, result.alias))
        } else {
            emit(null)
        }
    }

    override fun save(user: User) = flow {
        emit(userDAO.save(UserEntity(user.id, user.alias)))
    }

}
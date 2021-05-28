package org.isep.homeexchange.core.services.impl

import org.isep.homeexchange.core.dto.HousingDto
import org.isep.homeexchange.core.dto.UserDto
import org.isep.homeexchange.core.dto.toDao
import org.isep.homeexchange.core.services.HousingService
import org.isep.homeexchange.core.services.UserService
import org.isep.homeexchange.infrastructure.dao.HousingDao
import org.isep.homeexchange.infrastructure.dao.UserDao
import org.isep.homeexchange.infrastructure.dao.toDto
import org.isep.homeexchange.infrastructure.repository.HousingRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class HousingServiceImpl(
    private val housingRepository: HousingRepository,
    private val userService: UserService,
) : HousingService {

    override fun create(dto: HousingDto): HousingDto {
        val userDto = userService.getById(dto.userId)
        val housingDao = dto.toDao()
        housingDao.user = userDto.toDao()

        return housingRepository.save(housingDao).toDto()
    }

    override fun getById(id: Long): HousingDto {
        val housing: Optional<HousingDao> = housingRepository.findById(id);

        if (housing.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Housing doesn't exist")
        }

        return housing.get().toDto()
    }

    //Add by Benji
    override fun getByUsers(user_id: Long): List<HousingDto> {
        var userDto: UserDto = userService.getById(user_id)

        return userDto.housings
    }

    override fun getAll(): List<HousingDto> {
        val housingsDao: List<HousingDao> = housingRepository.findAll();

        return housingsDao.toDto()
    }

    override fun updateHousing(dto: HousingDto): HousingDto {
        //A voir si on utilise create a la place
        return dto
    }

    override fun deleteById(id: Long) {
        val housing: Optional<HousingDao> = housingRepository.findById(id)

        if(housing.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist")
        }

        housingRepository.deleteById(id)
    }

    override fun deleteAll() {
        housingRepository.deleteAll()
    }


    //


}
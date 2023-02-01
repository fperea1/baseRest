package com.base.rest.repositories;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, JpaSpecificationExecutor<BaseEntity> {

	Usuario getByUsername(String username);

	@Modifying
	@Query("update Usuario set activo = false, fechaDesactivacion = :fecha where id = :id")
	Integer deactivate(@Param("fecha") Date fecha, @Param("id") Integer id);

	@Modifying
	@Query("update Usuario set activo = true, fechaDesactivacion = null where id = :id")
	Integer activate(@Param("id") Integer id);
	
	@Modifying
	@Query("update Usuario set password = :password where id = :id")
	Integer changePassword(@Param("password") String password, @Param("id") Integer id);

	@Query("select password from Usuario where id = :id")
	String getPassword(@Param("id") Integer id);

}

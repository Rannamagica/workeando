package com.microservicios.workenado.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microservicios.workenado.model.Rol;
import com.microservicios.workenado.model.Usuario;
import com.microservicios.workenado.repository.RoleRepository;
import com.microservicios.workenado.repository.UsuarioRepository;

@Configuration
public class LoadDatabase {
    
    @Bean
    CommandLineRunner iniDatabase(RoleRepository roleRepo, UsuarioRepository userRepo){

        return arg ->{

            if(roleRepo.count()==0 && userRepo.count()==0){

                    Rol admin = new Rol();
                    admin.setNombre("Administrador ");
                    roleRepo.save(admin);

                    Rol user= new Rol();
                    user.setNombre("Cliente");
                    roleRepo.save(user);
                

                    Rol Rec= new Rol();
                    Rec.setNombre("Veterinario");
                    roleRepo.save(Rec);

                    Rol UserP=new Rol();
                    UserP.setNombre("gestor de inventario ");
                    roleRepo.save(UserP);


                    Rol soporte=new Rol();
                    soporte.setNombre("Soporte y administrador de sistemas");
                    roleRepo.save(soporte);

                    
                    

                   userRepo.save(new Usuario(null,"felipe","123","fege@123","fernando","villalobo",admin));
                    userRepo.save(new Usuario(null,"juan","321","dasCadez@123","gabriel","jorquera",user));
                    userRepo.save(new Usuario(null,"pepe","567","pera@123","estevan","torres",Rec));
                    userRepo.save(new Usuario(null,"daniela","5676","pera777@123","fortachon","gaspar",UserP));
                    userRepo.save(new Usuario(null,"victor","5676","pera777@12356","nicolas","sotomayor",soporte));

                    System.out.println("Datos iniciales cargados ");


            }else{

                System.out.println("Datos ya existen. no realizo carga");
            }



        };


    }
}

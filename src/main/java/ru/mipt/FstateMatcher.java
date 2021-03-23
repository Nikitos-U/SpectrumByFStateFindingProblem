//package ru.mipt;
//
//import ru.mipt.dao.DaoConfig;
//import ru.mipt.dao.ParticleRepository;
//
//import java.util.Objects;
//import java.util.Scanner;
//
//public class FstateMatcher {
//    private final DaoConfig config = new DaoConfig();
//    private final ParticleRepository repository = new ParticleRepository(config.getJdbcTemplate());
//
////    public Cascade prepareFstateByInput(String[] args) {
////        Scanner scanner = new Scanner(System.in);
////        System.out.println("Enter final state");
////        Cascade fstate = new Cascade();
////        String inputParticle = "";
////        if (args.length == 0 ) {
////            while (!inputParticle.equals("exit")) {
////                inputParticle = scanner.nextLine();
////                if (!inputParticle.equals("exit")) {
////                    fstate.getParticleList().add(Objects.requireNonNull(repository.findByName(inputParticle)));
////                }
////            }
////        } else {
////            for (String particle : args) {
////                fstate.getParticleList().add(Objects.requireNonNull(repository.findByName(particle)));
////            }
////        }
//        return fstate;
//    }
//}

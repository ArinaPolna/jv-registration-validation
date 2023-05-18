package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_STRING_PARAMETER_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNullParametersValue(user);
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
        checkIfUserExists(user);
        storageDao.add(user);
        return user;
    }

    private void checkNullParametersValue(User user) {
        if (user == null) {
            throw new InvalidDataException("User cant be null");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age cant be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password cant be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login cant be null");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("User's age must be over 18 to register");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin().equals("")) {
            throw new InvalidDataException("Login can't be empty");
        }
        if (user.getLogin().length() < MIN_STRING_PARAMETER_LENGTH) {
            throw new InvalidDataException("Login must be longer than 6 symbols to register");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword().length() < MIN_STRING_PARAMETER_LENGTH) {
            throw new InvalidDataException("Password must be longer than 6 symbols to register");
        }
    }

    private void checkIfUserExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("This user already exists");
        }
    }
}

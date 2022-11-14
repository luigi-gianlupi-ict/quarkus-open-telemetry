package it.ictgroup.config.service;

import it.ictgroup.config.annotation.ConfigParam;
import it.ictgroup.config.annotation.ConfigParamGetter;
import org.jboss.logging.Logger;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class AbstractModuleConfig implements ModuleConfig {

    final Logger LOG = Logger.getLogger(getClass());

    public AbstractModuleConfig() {
    }

    public abstract boolean getRefreshDefault();

    public void refreshConfig() {
        Arrays.stream(this.getClass().getDeclaredFields()).filter((f) -> f.isAnnotationPresent(ConfigParam.class)).forEach((f) -> {
            try {
                LOG.infof("Refreshing param %s", f.getName());
                f.setAccessible(true);
                if (Optional.class.equals(f.getType())) {
                    f.set(this, Optional.empty());
                } else {
                    f.set(this, null);
                }
            } catch (IllegalAccessException var3) {
                var3.printStackTrace();
            }

        });
    }

    protected void callAllConfigGetters() {
        List<Method> getters = Arrays.stream(this.getClass().getDeclaredMethods()).filter((m) -> m.isAnnotationPresent(ConfigParamGetter.class)).toList();
        getters.forEach((m) -> {
            try {
                m.setAccessible(true);
                LOG.infof("Calling method %s", m.getName());
                m.invoke(this);
            } catch (Exception var3) {
                LOG.error("Unable to invoke method " + m.getName() + ": " + var3.getMessage());
            }

        });
    }

    @SuppressWarnings("rawtypes,unused")
    public Map<String, Object> getAllConfig() {
        this.callAllConfigGetters();
        Map<String, Object> result = new HashMap<>();
        List<Field> configFields = Arrays.stream(this.getClass().getDeclaredFields()).filter((f) -> f.isAnnotationPresent(ConfigParam.class)).toList();
        configFields.forEach((f) -> {
            try {
                f.setAccessible(true);
                Object value = f.get(this);
                Object printable = value;
                if (value instanceof Optional) {
                    printable = ((Optional)value).orElse("");
                }

                LOG.info(f.getName() + " = " + printable);
                result.put(f.getName(), value);
            } catch (IllegalAccessException var5) {
                var5.printStackTrace();
            }

        });
        return result;
    }
}

package cn.org.wangchangjiu.rpc.core.balancer;

import cn.org.wangchangjiu.rpc.core.common.ServiceInfo;

import java.util.List;
import java.util.Random;

/**
 * 随机算法
 */
public class RandomBalance implements LoadBalance{

    private static Random random = new Random();

    @Override
    public ServiceInfo chooseOne(List<ServiceInfo> services) {
        return services.get(random.nextInt(services.size()));
    }
}

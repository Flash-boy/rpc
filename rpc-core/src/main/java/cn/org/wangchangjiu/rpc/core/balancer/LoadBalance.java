package cn.org.wangchangjiu.rpc.core.balancer;

import cn.org.wangchangjiu.rpc.core.common.ServiceInfo;

import java.util.List;

/**
 * 负载均衡算法接口
 */
public interface LoadBalance {
    /**
     *
     * @param services
     * @return
     */
    ServiceInfo chooseOne(List<ServiceInfo> services);
}

# 论文

# Content Delivery Networks - Q-Learning

## Intro

### Approach

Study CDN using the traffic model

### 实现

两种算法：an optimal approach using Brute Force and machine learning-based approach, namely Q-Learning.

## Related Works

ML-based models can be estimated to predict the evolution of the relative popularity of a group of content.

收集数据：Real User Monitoring (RUM) and application monitor- ing (APM) tools.

## Problem

理想化时，所有资源都应存储在CDN server中。但是operating, storage costs和用户需求随地点和时间在变。所以要找到一个自适应大小的服务来减小cost，同时维持CDN表现。

主要目标：减小OPEX costs of CDN by increasing the TTL of content stored on the server.  为了增加TTL，要提高Cache Hit Ratio.

## CDN Model

Model可以记录CDN cost ($ for 100,00 requests)和Cache Hit Rate.

Content指标：size, popularity level.

Cache node存content、记录requests数量。它会给content设置TTL，基于popularity. 过期后会向main server重新请求。

## Algorithms

暴力法算最佳太慢了，不满足现实网络要求。为了克服过长的模拟过程，实现了Q-Learning.

Q-learning 步骤：

When the simulation starts, we assign a random TTL starting value and calculate its cost recursively. Then, depends on the reward value, we exercise the following possible actions:

- add 1 day to current TTL,
- deduct 1 day from current TTL,
- add 7 days to current TTL,
- deduct 7 days from current TTL,
- (current TTL + maximum TTL) / 2,
- (current TTL + minimum TTL) / 2.

## 仿真设置



## Results

Cache hit ratio提高0.64%（有限），但对比暴力法消耗时间大大减少（52,723s → 517s）。





# On Designing a Cost-Aware Virtual CDN for the Federated Cloud

## 1. Intro

Main challenge: while establishing such a CDN is implementing a cost efficient and dynamic mechanism which guarantees good service quality to users.

设计了一个model帮助MPs建立self-managed virtual CDN by leveraging a OpenStack- based federated cloud.

Perfomance assessed over the federated XIFI cloud.

## 2. Framework for virtual CDN

用户按地区分组 - based on Classless Inter-Domain Routing (CIDR) address blocks.

We used a non-cooperative pull- based mechanism for building a centralized virtual CDN

We developed an algorithm that manages caching proxies based on the demand from user clusters.

## 3. The Cost Function

使用virtual proxy的费用包含open cost, cost of streaming a video from virtual proxy to uses, cost of transferring a video from origin server to a virtual proxy三部分。

## 4. Heuristic algorithm for virtual CDN

The goal of this algorithm is to spawn or turn off caching proxies in response to varying user demand in a cluster. 

## 5. Testbed

4个指标

## 6. Results


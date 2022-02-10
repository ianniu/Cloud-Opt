# Papers

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

理想化时，所有资源都应存储在 CDN server 中。但是 operating, storage costs 和用户需求随地点和时间在变。所以要找到一个自适应大小的服务来减小 cost，同时维持 CDN 表现。

主要目标：减小 OPEX costs of CDN by increasing the TTL of content stored on the server. 为了增加 TTL，要提高 Cache Hit Ratio.

## CDN Model

Model 可以记录 CDN cost ($ for 100,00 requests)和 Cache Hit Rate.

Content 指标：size, popularity level.

Cache node 存 content、记录 requests 数量。它会给 content 设置 TTL，基于 popularity. 过期后会向 main server 重新请求。

## Algorithms

暴力法算最佳太慢了，不满足现实网络要求。为了克服过长的模拟过程，实现了 Q-Learning.

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

Cache hit ratio 提高 0.64%（有限），但对比暴力法消耗时间大大减少（52,723s → 517s）。

## 问题

- BF 和 Q 的关系
  - BF 的结论是完全正确的么？BF 作为标准对照组？还是作为优化的起始点？BF准确率求证，目的是时间的优化。
- Algorithm
  - 各项数值怎么计算，CHR，TTL，cost - simulator
  - 为什么会有 cache misses，怎么选择缓存的部分
  - cost 和存多少数据有关么
  - TTL 和请求数量有关么 - 初始值设定，步进？
  - if reward > 0 then "randomly" - why?
- 怎样实现
  - Q:ML
  - BF

# On Designing a Cost-Aware Virtual CDN for the Federated Cloud

## 1. Intro

Main challenge: while establishing such a CDN is implementing a cost efficient and dynamic mechanism which guarantees good service quality to users.

设计了一个 model 帮助 MPs 建立 self-managed virtual CDN by leveraging a OpenStack- based federated cloud.

Perfomance assessed over the federated XIFI cloud.

## 2. Framework for virtual CDN

用户按地区分组 - based on Classless Inter-Domain Routing (CIDR) address blocks.

We used a non-cooperative pull- based mechanism for building a centralized virtual CDN

We developed an algorithm that manages caching proxies based on the demand from user clusters.

## 3. The Cost Function

使用 virtual proxy 的费用包含 open cost, cost of streaming a video from virtual proxy to uses, cost of transferring a video from origin server to a virtual proxy 三部分。

## 4. Heuristic algorithm for virtual CDN

The goal of this algorithm is to spawn or turn off caching proxies in response to varying user demand in a cluster.

## 5. Testbed

4 个指标

## 6. Results

# CDN Opt
This is a group project of CS 5800. 

Content Delivery Networks - Gradient Descent Approach for Optimization of the Network Cost and the Cache Hit Ratio.



## Dev instruction

To develop on your computer, checkout a new Git branch and do your work. **Do not develop on the master branch!**

Before you upload your work, please invite someone to do a code review.

Every time you want to upload your work, please first checkout to master branch and pull:

```bash
gco master
git pull --rebase origin master
```

Then merge your branch to master branch:

```
git merge --squash your-branch-name
```

First solve the conflict (if there's any). Then add, commit, and push to the master.



## Abstract

Based on the Q-Learning approach for optimization of the network cost and the cache hit ratio, we brought up a new machine learning based approach to find the best trade-off between network cost and cache hit ratio.

A brief introduction can be seen in this video: https://vimeo.com/704016222/4ec9a95672



## Background

To reduce response time, streaming services like Netflix tend to put more and more contents on CDN. However, with much use,  the CDN cost is relevant high. So it is necessary to adjust how long should one certain content be stored in CDN automatically based on the content demand.



## Goal

Based on the content delivery network model, the optimal TTL for some randomly generated contents should be found quickly.

The brute force approach to find the optimal TTL is the benchmark for balancing network cost and cache hit ratio, but the time of brute force is not practical for large amount of web contents.

So the approach produces accurate optimal TTL with much less execution time should be designed and implemented.



## Solution

A content delivery network model is implemented. This model contains a main server, several regions, cache nodes, clients, contents to be requested, etc. A gradient descent algorithm is designed and used for finding the optimal TTL for 100 web contents with different popularities. The Cache Hit Ratio should be greater than 45% and the overall cost should be the lowest under the optimal TTL.

We also set three different lifetime in our simulation: 12 weeks, 25 weeks, and 50 weeks, to test the effectiveness and efficiency of our algorithmssolidify our conclusion.



## Results

Results can be seen in our paper.




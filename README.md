# Cloud-Opt
This is a group project of CS 5800.



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

"This is the abstract of our project and the problem we solve."

Based on the Q-Learning approach for optimization of the network cost and the cache hit ratio, we want to bring up a new machine learning based approach to find the best trade-off between network cost and cache hit ratio.



## Background

"Based on what scenario we want to do this project."

To reduce response time, streaming services like Netflix tend to put more and more contents on CDN. However, with much use,  the CDN cost is relevant high. So it is necessary to adjust how long should one certain content be stored in CDN automatically based on the content demand.



## Goal

"Some goals we want to achieve."

There are two possible solutions to find the best trade-off between network cost and CHR.

1. Based on the traffic model, we improve the TTL algorithm to achieve a higher CHR or reduce simulation time.
2. By comparing traffic model and other similar models, find another suitable model and use it to implement our algorithm to improve CHR or reduce simulation time.

But still there are several questions ahead of us:

1. Is the brute force approach brought up in the paper a fully accurate algorithm? If so, we can use it as a benchmark. Based on what we've read, the brute force approach is used to calculate the optimal solution. And the algorithm is used to reduce simulation time.
2. The initail value of TTL was randomly selected in the algorithm. Is there a better way to set a more precise value based on some predictions about the content? (Reduce some simulation time) 
3. In simulation, depends on the reward value, some actions about adding / deducting TTL was performed. (Like adding 1 day / deducting 7 days) Is it effective to have some smaller change scope for TTL to achieve better simulation time?
4. In the algorithm, it says "if reward > 0, then randomly choose the next action and repeat the reward calculation". Why choose randomly? How it promised the effect?



## Solution

What we do to achieve.



## Open issue

There are some aspects in our project that can be discussed further.



## One more thing...

We still can go further.


package org.kehrbusch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactiveTest {
    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;
    private ReactiveListOperations<String, String> reactiveListOps;
    private ReactiveHashOperations<String, String, String> reactiveHashOperations;

    @Before
    public void setup() {
        reactiveListOps = redisTemplate.opsForList();
        reactiveHashOperations = redisTemplate.opsForHash();
    }

    @Test
    public void givenListAndValues_whenLeftPushAndLeftPop_thenLeftPushAndLeftPop() {
        Mono<Long> lPush = reactiveListOps.leftPushAll("list", "first", "second")
                .log("Pushed");
        StepVerifier.create(lPush)
                .expectNext(2L)
                .verifyComplete();
        Mono<String> lPop = reactiveListOps.leftPop("list")
                .log("Popped");
        StepVerifier.create(lPop)
                .expectNext("second")
                .verifyComplete();
        Mono<String> lPop2 = reactiveListOps.leftPop("list");
        StepVerifier.create(lPop2)
                .expectNext("first")
                .verifyComplete();
    }

    @Test
    public void testThatPushKeyWorks(){
        Map<String, String> map = new HashMap<>();
        map.put("key1","val1");
        map.put("key2","val2");
        reactiveHashOperations.putAll("key1", map).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(2);
            }

            @Override
            public void onNext(Boolean aBoolean) {
                System.out.println(aBoolean);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });

        reactiveHashOperations.entries("key1").subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(2);
            }

            @Override
            public void onNext(Map.Entry<String, String> entry) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable);
            }

            @Override
            public void onComplete() {
                System.out.println("completed");
            }
        });

        reactiveHashOperations.get("key1", "key1").subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(2);
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("completed");
            }
        });

        reactiveHashOperations.delete("key1").subscribe(new Subscriber<Boolean>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(1);
            }

            @Override
            public void onNext(Boolean aBoolean) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("deleted");
            }
        });

        reactiveHashOperations.get("key1","key1").subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(1);
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("completed");
            }
        });
    }

    @Test
    public void testThatGetHashListIsWorking(){
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");

        reactiveHashOperations.putAll("key", map).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(1);
            }
            @Override
            public void onNext(Boolean aBoolean) {
            }
            @Override
            public void onError(Throwable throwable) {
            }
            @Override
            public void onComplete() {
            }
        });

        reactiveListOps.leftPush("key:children", "value").subscribe(new Subscriber<Long>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(1);
            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
        reactiveListOps.leftPush("key:children", "value1").subscribe(new Subscriber<Long>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(1);
            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });

        reactiveListOps.range("key:children",0,-1).subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}

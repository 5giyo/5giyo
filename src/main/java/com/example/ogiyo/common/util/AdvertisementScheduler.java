package com.example.ogiyo.common.util;

import com.example.ogiyo.domain.advertisement.entity.Advertisement;
import com.example.ogiyo.domain.advertisement.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.example.ogiyo.domain.advertisement.entity.Advertisement.Status.*;

import java.time.LocalDateTime;
import java.util.List;

/*
* Scheduler 의 사용 이유
* 광고 상태를 광고의 시작 날짜 와 종료날짜, 그리고 현재 날짜를 기준으로 결정
* 만약 오늘 이 광고의 종료 날짜를 넘어갔다면 광고의 상태는 ADVERTISING -> NON_ADVERTISING 으로 변경돼야함
* 이 작업은 하루에 한번씩 진행돼야 하기 때문에 일정주기로 메서드를 호출하는 @Scheduled 가 적합하다고 생각하여 사용함

* 주의해야 할 점
* 사용하기 간단하지만, 스케줄러의 특성상 단일서버로 동작하는 어플리케이션에 적합함.
* 여러대의 서버로 운영되는 어플리케이션에서는 특정 경우를 제외하고는 사용할 경우 예상치 못한 사이드 이펙트가 발생할 수 있음
* 여러대의 서버가 동시에 스케줄링을 할 경우 같은 작업이 동시에 반복적으로 일어날 수 있음. 이는 한번의 처리만 필요한 경우에도 여러번 처리가 되는 문제가 됨
* 예를 들면, 사용자에게 특정시간에 알림을 보내는 스케줄러가 있을 경우, 한번만 보내야 함에도 불구하고 스케줄러가 있는 서버 수 만큼 보낼 수 있음
* 따라서 여러대의 서버로 운영되는 어플리케이션의 경우는 배치나 스케줄링 담당 서버를 만들어서 처리하는 방식 등을 고려해볼 수 있음
* */

@Slf4j
@Component
@RequiredArgsConstructor
public class AdvertisementScheduler {
    private final AdvertisementService advertisementService;

    // 테스트용
    // @Scheduled(fixedRate = 10000)
    // 매일 새벽 6시 스케줄링
    @Scheduled(cron = "0 0 6 * * ?")
    @Transactional
    public void runTask() {
        log.info("Scheduler Start: {}", LocalDateTime.now());
        List<Advertisement> advertisements = advertisementService.findAllAdvertisements();

        for (Advertisement advertisement : advertisements) {
            advertisement.updateStatus(checkStatus(advertisement.getStartedAt(), advertisement.getEndedAt()));
        }
        log.info("Scheduler End: {}", LocalDateTime.now());
    }
}

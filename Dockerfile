# Базовый образ: агент Jenkins с Java 17 для архитектуры amd64 (aapt2 x86-64)
FROM --platform=linux/amd64 jenkins/inbound-agent:jdk17

# Смена пользователя на root. Для установки пакетов нужны права root
USER root

# Системные утилиты: скачивание, распаковка, git
RUN apt-get update && apt-get install -y --no-install-recommends \
   wget unzip curl git bc && rm -rf /var/lib/apt/lists/*

# Путь к Android SDK и добавление его инструментов в PATH
ENV ANDROID_HOME=/opt/android-sdk
ENV PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# Параметр сборки: номер версии инструментов Google
ARG CMDLINE_TOOLS=11076708
# Скачивание и установка sdkmanager (установщик компонентов Android)
RUN mkdir -p $ANDROID_HOME/cmdline-tools \
 && curl -fsSL https://dl.google.com/android/repository/commandlinetools-linux-${CMDLINE_TOOLS}_latest.zip -o /tmp/t.zip \
 && unzip -q /tmp/t.zip -d $ANDROID_HOME/cmdline-tools \
 && mv $ANDROID_HOME/cmdline-tools/cmdline-tools $ANDROID_HOME/cmdline-tools/latest \
 && rm /tmp/t.zip

# Установка компонентов SDK + смена владельца папки SDK на пользователя jenkins
# platforms;android-** - должен совпадать с compileSdk
# build-tools;** — инструменты упаковки APK/AAB, должны совпадать с buildToolsVersion/Android SDK Build tools
RUN set +o pipefail \
 && yes | sdkmanager --licenses > /dev/null \
 && sdkmanager "platform-tools" "platforms;android-37.0" "build-tools;37.0.0" \
 && chown -R jenkins:jenkins $ANDROID_HOME

# Установка gradle-profiler (авто-запуск проекта и замеры скорости сборки)
# github.com/gradle/gradle-profiler/releases
ARG PROFILER=0.26.0
RUN wget -q https://github.com/gradle/gradle-profiler/releases/download/v${PROFILER}/gradle-profiler-${PROFILER}.zip -O /tmp/p.zip \
 && unzip -q /tmp/p.zip -d /opt && mv /opt/gradle-profiler-${PROFILER} /opt/gradle-profiler \
 && ln -s /opt/gradle-profiler/bin/gradle-profiler /usr/local/bin/gradle-profiler && rm /tmp/p.zip

# Смена пользователя на jenkins
USER jenkins
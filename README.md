# InlineLog

This is a small Android logging library, based off of `android.util.Log`. The key feature is that it
uses inline functions for logging and has a no-op implementation for the release variant, so your 
logs are "compiled out" for release variants of your app.

This library also encourages lazy logging, i.e. 

```
Log.i(TAG) { "message is constructed in a lambda which is evaluated lazily: $foo" }`
```

rather than

```
Log.i(TAG, "message is constructed eagerly, even if the log isn't printed: $foo" }`
```

This library has two benefits over just using lazy logging for release variants of apps, due to the 
logging code being compiled out:

- Your code doesn't create any unused `Function()` objects, which need to be eventually garbage 
collected. It also doesn't have to go through any if tests.

- Hackers decompiling your bytecode don't see your human-readable logs.

## Alternative solutions

Instead of using this library, you could instead just use the following proguard rules to compile
out calls to `android.util.Log`:

```
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
    public static *** wtf(...);
}
```

Using this library, you do not need any special proguard rules.

## Usage

In your top level `build.gradle` file, include a repository that hosts this library (TODO).

```
allprojects {
    repositories {
        google()
        mavenCentral()
        ...
        maven { url 'https://the.repository.com' }  // Add this line
    }           
}
```

Then, in your app or module's `build.gradle` file, include the following lines:

```
dependencies {
    ...
    releaseImplementation 'com.wyvernlabs.inlinelog:1.0.0'
    debugImplementation 'com.wyvernlabs.inlinelog-debug:1.0.0'
}
```

Now, simply replace your imports of `android.util.Log` with `com.wyvernlabs.inlinelog.Log`. You will
be prompted to migrate to using lambdas for your log messages via a deprecation warning, but you may
still build without doing this.

You can customize the logger used and the severity level that the logger should use with 
`Log.logger` and `Log.severity`.

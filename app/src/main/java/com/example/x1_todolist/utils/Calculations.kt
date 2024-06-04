package com.example.x1_todolist.utils

import com.example.x1_todolist.utils.Calculations.calculateRemainingTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


object Calculations {

    fun getCurrentDateTime(): Pair<String, String> {
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        return Pair(currentDate, currentTime)
    }


    fun calculateRemainingTime(deadline: String): String {
        val pattern = "dd/MM/yyyy HH:mm"
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())

        try {
            // Şu anki zamanı al
            val currentTime = Calendar.getInstance().time

            // Verilen tarihi belirtilen formata çevir
            val deadlineDate = formatter.parse(deadline)

            // Verilen tarihle şu anki tarih arasındaki farkı hesapla
            val diffInMillis = deadlineDate.time - currentTime.time

            // Farkı yıl, ay, gün, saat ve dakika cinsine çevir
            val years = diffInMillis / (1000L * 60 * 60 * 24 * 365)
            val months = (diffInMillis % (1000L * 60 * 60 * 24 * 365)) / (1000L * 60 * 60 * 24 * 30)
            val days = (diffInMillis % (1000L * 60 * 60 * 24 * 30)) / (1000L * 60 * 60 * 24)
            val hours = (diffInMillis % (1000L * 60 * 60 * 24)) / (1000L * 60 * 60)
            val minutes = ((diffInMillis % (1000L * 60 * 60 * 24)) % (1000L * 60 * 60)) / (1000L * 60)

            // Kalan süreyi string olarak oluştur
            val remainingTime = StringBuilder()

            if (diffInMillis < 0) {
                if (Math.abs(years)>0){
                    remainingTime.append("${Math.abs(years)} yıl ")
                }
                if (Math.abs(months)>0){
                    remainingTime.append("${Math.abs(months)} ay ")
                }
                if (Math.abs(days)>0){
                    remainingTime.append("${Math.abs(days)} gün ")
                }
                if (Math.abs(hours)>0){
                    remainingTime.append("${Math.abs(hours)} saat ")
                }
                if (Math.abs(minutes)>0){
                    remainingTime.append("${Math.abs(minutes)} dk ")
                }
                remainingTime.append("have passed")

            } else {
                if (years > 0) {
                    remainingTime.append("$years years ")
                }
                if (months > 0) {
                    remainingTime.append("$months months ")
                }
                if (days > 0) {
                    remainingTime.append("$days days ")
                }
                if (hours > 0) {
                    remainingTime.append("$hours hours ")
                }
                if (minutes > 0) {
                    remainingTime.append("$minutes minutes ")
                }
                remainingTime.append("left")
            }

            return remainingTime.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return "invalid date format"
        }
    }

    fun calculateTimeRemainingWithSeconds(taskDeadline: String): String {
        // Task deadline string'ini "dd/MM/yyyy HH:mm" formatında pars etme
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val deadlineDate = dateFormat.parse(taskDeadline)

        // Task deadline'ı saniyesiyle birlikte "dd/MM/yyyy HH:mm:ss" formatına dönüştürme
        val deadlineCalendar = Calendar.getInstance()
        deadlineCalendar.time = deadlineDate
        deadlineCalendar.set(Calendar.SECOND, 0)
        val deadlineWithSeconds = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(deadlineCalendar.time)

        // Şu anki tarih ve saat bilgisini alma
        val currentCalendar = Calendar.getInstance()
        val currentTime = currentCalendar.time

        // Task deadline'ı ve şu anki zaman arasındaki farkı hesaplama
        val remainingTimeMillis = deadlineCalendar.timeInMillis - currentCalendar.timeInMillis

        // Farkı yıl, ay, gün, saat, dakika ve saniyeye dönüştürme
        val remainingCalendar = Calendar.getInstance()
        remainingCalendar.timeInMillis = Math.abs(remainingTimeMillis)

        val years = remainingCalendar.get(Calendar.YEAR) - 1970
        val months = remainingCalendar.get(Calendar.MONTH)
        val days = remainingCalendar.get(Calendar.DAY_OF_MONTH) - 1
        val hours = remainingCalendar.get(Calendar.HOUR_OF_DAY)
        val minutes = remainingCalendar.get(Calendar.MINUTE)
        val seconds = remainingCalendar.get(Calendar.SECOND)

        // Sonuç ifadesini oluşturma
        val result = StringBuilder()

        if (years > 0) {
            result.append("$years yıl ")
        }
        if (months > 0) {
            result.append("$months ay ")
        }
        if (days > 0) {
            result.append("$days gün ")
        }
        if (hours > 0) {
            result.append("$hours saat ")
        }
        if (minutes > 0) {
            result.append("$minutes dakika ")
        }
        if (seconds > 0) {
            result.append("$seconds saniye ")
        }

        if (remainingTimeMillis < 0) {
            result.append("önceydi")
        } else {
            result.append("kaldı")
        }

        return result.toString()
    }
}




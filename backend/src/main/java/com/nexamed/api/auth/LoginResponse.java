package com.nexamed.api.auth;

public record LoginResponse(String token, String tipo, long expiraEmMinutos) {}
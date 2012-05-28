//
//  PADtextViewController.m
//  iPhoneDEAF
//
//  Created by developer on 12. 1. 10..
//  Copyright (c) 2012 HeadFlow All rights reserved.
//

#import "PADtextViewController.h"
#import <sys/types.h>
#import <sys/socket.h>
#import <netinet/in.h>
#import <netdb.h>
void CFSockCall (
                 CFSocketRef s,
                 CFSocketCallBackType callbackType,
                 CFDataRef address,
                 const void *data,
                 void *info
                 ) 
{
    PADtextViewController * self = (__bridge id)info;
    
    if(callbackType == kCFSocketReadCallBack) {
        char buf[600] = {'\0'};
        int sock = CFSocketGetNative(s);
        recv(sock, &buf, 600, 0);

        NSString *sData = [NSString stringWithUTF8String: (const char*)buf+2];
        if (sData.length > 0 && sData.length < 201)
        {
            self.padShowView.text = sData;
            [self.padShowView scrollRangeToVisible:NSMakeRange([self.padShowView.text length], 0)];
            self.tempstring = [NSString stringWithString:sData];
        }
        else
        {
            self.padShowView.text = self.tempstring;
        }
        
        
        //send data to server
        const UInt8 sendbuf[1] = {0};   
        CFDataRef dt = CFDataCreate(NULL, sendbuf, 1);
        CFSocketSendData(s, NULL, dt, 1);  

        
    }
    if(callbackType == kCFSocketConnectCallBack) {
        
    }
}



@implementation PADtextViewController
@synthesize padShowView;

@synthesize padipsend;
@synthesize padportsend;
@synthesize tempstring;
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

/*
 // Implement loadView to create a view hierarchy programmatically, without using a nib.
 - (void)loadView
 {
 }
 */


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad
{
    [super viewDidLoad];

    [self Socket];
}


- (void) Socket{
    
    
    const CFSocketContext socketContext = { 0, self, NULL, NULL, NULL };
    
    
    CFSocketRef ref = CFSocketCreate(kCFAllocatorDefault, PF_INET, SOCK_STREAM, 0, 
                                     kCFSocketReadCallBack|kCFSocketConnectCallBack,
                                     CFSockCall,&socketContext );
    struct sockaddr_in theName;
    struct hostent *hp;
    
    
    NSString *nsipsend =  self.padipsend;
    NSString *nsportsend = self.padportsend;
    
    int iPort = [nsportsend intValue];
    theName.sin_port = htons(iPort);
    theName.sin_family = AF_INET;
    
    const char* ipAddress = [nsipsend cStringUsingEncoding:NSUTF8StringEncoding]; 
    
    hp = gethostbyname(ipAddress);
    if( hp == NULL ) {
        return;
    }
    memcpy( &theName.sin_addr.s_addr, hp->h_addr_list[0], hp->h_length );
    
    CFDataRef addressData = CFDataCreate( NULL, &theName, sizeof( struct sockaddr_in ) );
    CFSocketConnectToAddress(ref, addressData, 30);
    
    CFRunLoopSourceRef FrameRunLoopSource = CFSocketCreateRunLoopSource(NULL, ref , 0);
    CFRunLoopAddSource(CFRunLoopGetCurrent(), FrameRunLoopSource, kCFRunLoopCommonModes);  
}



- (void)viewDidUnload
{
    [self setPadShowView:nil];
    
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return YES;
}

- (void)willAnimateRotationToInterfaceOrientation:(UIInterfaceOrientation)
interfaceOrientation duration:(NSTimeInterval)duration {
    
    if (UIInterfaceOrientationIsPortrait(interfaceOrientation)) { 
        padShowView.frame = CGRectMake(0, 0, 768, 960);
        
    } 
    else
    {
        padShowView.frame = CGRectMake(0, 0, 1030, 710);
    }
}



- (void)dealloc {
    [padShowView release];
    [super dealloc];
}

@end
